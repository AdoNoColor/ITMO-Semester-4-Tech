package com.AdoNoColor.repository;

import com.AdoNoColor.domain.entity.Breed;
import com.AdoNoColor.domain.entity.Cat;
import com.AdoNoColor.domain.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface CatRepository extends JpaRepository<Cat, Integer> {
    Collection<Cat> getByColor(Color color);
    Collection<Cat> getByBreed(Breed breed);
}
