package com.example.active.business.service;

import com.example.active.business.domain.TypeDTO;
import com.example.active.data.entity.Type;

import java.util.List;

public interface TypeService {
    List<TypeDTO> findAll(String query, String sort);
    void save(Type type);

    void deleteType(Long id);
}
