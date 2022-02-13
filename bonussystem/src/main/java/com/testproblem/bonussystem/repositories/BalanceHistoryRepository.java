package com.testproblem.bonussystem.repositories;

import com.testproblem.bonussystem.models.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
	List<BalanceHistory> findByIdAndCreated_atGreaterThanAndCreated_atLessThan(Long id, Date start, Date end);
}
