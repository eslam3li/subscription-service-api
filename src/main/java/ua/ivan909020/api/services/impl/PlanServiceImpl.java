package ua.ivan909020.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.repositories.PlanRepository;
import ua.ivan909020.api.services.PlanService;

@Service
public class PlanServiceImpl implements PlanService {

	private final PlanRepository repository;

	@Autowired
	public PlanServiceImpl(PlanRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean existsById(Integer planId) {
		if (planId == null) {
			throw new IllegalArgumentException("Plan ID must not be NULL");
		}
		return repository.existsById(planId);
	}

	@Override
	public Plan findById(Integer planId) {
		if (planId == null) {
			throw new IllegalArgumentException("Plan ID must not be NULL");
		}
		return repository.findById(planId).orElse(null);
	}

	@Override
	public Plan create(Plan plan) {
		if (plan == null) {
			throw new IllegalArgumentException("Plan must not be NULL");
		}
		if (plan.getId() != null) {
			throw new ValidationException("Plan ID must be NULL");
		}
		return repository.save(plan);
	}

	@Override
	public Plan update(Plan plan) {
		if (plan == null) {
			throw new IllegalArgumentException("Plan must not be NULL");
		}
		if (plan.getId() == null) {
			throw new ValidationException("Plan ID must not be NULL");
		}
		return repository.save(plan);
	}

	@Override
	public void deleteById(Integer planId) {
		if (planId == null) {
			throw new IllegalArgumentException("Plan ID must not be NULL");
		}
		repository.deleteById(planId);
	}

	@Override
	public Page<Plan> findAll(Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable must not be NULL");
		}
		return repository.findAll(pageable);
	}

}
