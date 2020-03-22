package ua.ivan909020.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.ivan909020.api.domain.dao.Subscription;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.repositories.SubscriptionRepository;
import ua.ivan909020.api.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository repository;

	@Autowired
	public SubscriptionServiceImpl(SubscriptionRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean existsById(Integer subscriptionId) {
		if (subscriptionId == null) {
			throw new IllegalArgumentException("Subscription ID must not be NULL");
		}
		return repository.existsById(subscriptionId);
	}

	@Override
	public Subscription findById(Integer subscriptionId) {
		if (subscriptionId == null) {
			throw new IllegalArgumentException("Subscription ID must not be NULL");
		}
		return repository.findById(subscriptionId).orElse(null);
	}

	@Override
	public Subscription create(Subscription subscription) {
		if (subscription == null) {
			throw new IllegalArgumentException("Subscription must not be NULL");
		}
		if (subscription.getId() != null) {
			throw new ValidationException("Subscription ID must be NULL");
		}
		return repository.save(subscription);
	}

	@Override
	public Subscription update(Subscription subscription) {
		if (subscription == null) {
			throw new IllegalArgumentException("Subscription must not be NULL");
		}
		if (subscription.getId() == null) {
			throw new ValidationException("Subscription ID must not be NULL");
		}
		return repository.save(subscription);
	}

	@Override
	public void deleteById(Integer subscriptionId) {
		if (subscriptionId == null) {
			throw new IllegalArgumentException("Subscription ID must not be NULL");
		}
		repository.deleteById(subscriptionId);
	}

	@Override
	public Page<Subscription> findAll(Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable must not be NULL");
		}
		return repository.findAll(pageable);
	}

}
