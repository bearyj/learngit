package com.jit.robert.serviceinterface;

import com.jit.robert.domain.Pound;

import java.util.List;

public interface PoundService {
    Pound insertPound(Pound pound);
    Boolean deletePoundById(Integer id);
    Pound updatePoundById(Integer id,Pound pound);
    List<Pound> getAllPoundsByCustomer();

}
