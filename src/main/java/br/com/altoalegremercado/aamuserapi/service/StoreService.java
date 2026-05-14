package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.StoreDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;

import java.util.List;

public interface StoreService {

    List<Store> getAllStores();

    Store getStoreById(Long id);

    Store createStore(StoreDTO storeDTO);

    Store updateStore(Long id, StoreDTO storeDTO) throws Exception;

    void deleteStore(Long id) throws Exception;

    List<Store> getActiveStores();
}
