package com.testproblem.bonussystem.repositories;

import com.testproblem.bonussystem.models.Card;
import com.testproblem.bonussystem.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
	Optional<Card> findByIdAndIsActive(Long id, Boolean is_active);
	Optional<Card> findByClientIdAndNumberAndIsActive(Long client, Integer number, Boolean is_active);

}
