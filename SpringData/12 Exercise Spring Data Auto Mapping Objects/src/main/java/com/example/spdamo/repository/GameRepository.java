package com.example.spdamo.repository;

import com.example.spdamo.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game getByTitle(String title);
}
