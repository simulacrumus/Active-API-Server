package com.example.active.data.repository;

import com.example.active.data.entity.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    List<Type> findByTitleContains(String title, Sort sort);

    List<Type> findByTitleContains(String title);
}
