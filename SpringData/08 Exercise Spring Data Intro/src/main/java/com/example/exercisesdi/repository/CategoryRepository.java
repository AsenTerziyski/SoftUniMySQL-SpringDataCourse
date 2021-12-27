package com.example.exercisesdi.repository;

import com.example.exercisesdi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository:
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
