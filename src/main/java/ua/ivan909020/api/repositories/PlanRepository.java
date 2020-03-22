package ua.ivan909020.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivan909020.api.domain.dao.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

	Page<Plan> findAll(Pageable pageable);

}
