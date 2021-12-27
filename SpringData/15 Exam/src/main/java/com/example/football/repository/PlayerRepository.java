package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

   @Query ("select p from Player as p order by p.stat.shooting desc, p.stat.passing desc , p.stat.endurance desc, p.lastName")
   List<Player> OrderPlayerByShootingDescOrderThenByPassingInDescOrderThenByEnduranceDescOrderFinallyByPlayerLastName();

}
