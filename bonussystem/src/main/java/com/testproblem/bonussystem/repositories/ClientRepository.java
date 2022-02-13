package com.testproblem.bonussystem.repositories;

import com.testproblem.bonussystem.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByIdAndIsActive(Long id, Boolean is_active);
}
