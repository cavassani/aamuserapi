package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.StoreDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Address;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getAllStores() {
        return (List<Store>) storeRepository.findAll();
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public Store createStore(StoreDTO dto) {
        Store store = new Store();
        store.setName(dto.getName());
        store.setCnpj(dto.getCnpj());
        store.setPhone(dto.getPhone());
        store.setEmail(dto.getEmail());
        store.setActive(dto.getActive() != null ? dto.getActive() : true);

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setZipCode(dto.getZipCode());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setDistrict(dto.getDistrict());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        store.setAddress(address);

        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Long id, StoreDTO dto) throws Exception {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new Exception("Store with ID (" + id + ") not found!"));

        store.setName(dto.getName());
        store.setCnpj(dto.getCnpj());
        store.setPhone(dto.getPhone());
        store.setEmail(dto.getEmail());
        store.setActive(dto.getActive());

        if (store.getAddress() != null) {
            Address address = store.getAddress();
            address.setStreet(dto.getStreet());
            address.setZipCode(dto.getZipCode());
            address.setNumber(dto.getNumber());
            address.setComplement(dto.getComplement());
            address.setDistrict(dto.getDistrict());
            address.setCity(dto.getCity());
            address.setState(dto.getState());
            address.setCountry(dto.getCountry());
        }

        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long id) throws Exception {
        if (!storeRepository.findById(id).isPresent()) {
            throw new Exception("Store with ID (" + id + ") not found!");
        }
        storeRepository.deleteById(id);
    }

    @Override
    public List<Store> getActiveStores() {
        return storeRepository.findByActive(true);
    }
}
