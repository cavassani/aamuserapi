package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.StoreDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.domain.model.User;

import java.util.List;

public interface StoreService {

    List<Store> getAllStores();

    Store getStoreById(Long id);

    Store createStore(StoreDTO storeDTO, User owner);

    Store updateStore(Long id, StoreDTO storeDTO) throws Exception;

    void deleteStore(Long id) throws Exception;

    List<Store> getActiveStores();

    List<Store> getStoresByOwner(User owner);

    List<Store> searchByName(String name);
}
