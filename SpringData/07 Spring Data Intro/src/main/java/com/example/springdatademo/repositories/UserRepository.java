package com.example.springdatademo.repositories;

import com.example.springdatademo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository -> по дефиция е репозитори, така че няма нужда.
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
