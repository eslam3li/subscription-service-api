package ua.ivan909020.api.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import ua.ivan909020.api.annotations.ApiPageable;
import ua.ivan909020.api.domain.dao.Subscription;
import ua.ivan909020.api.domain.dto.SubscriptionDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.mappers.SubscriptionMapper;
import ua.ivan909020.api.services.PlanService;
import ua.ivan909020.api.services.SubscriptionService;
import ua.ivan909020.api.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionRestController {

	private final SubscriptionService subscriptionService;
	private final UserService userService;
	private final PlanService planService;
	private final SubscriptionMapper subscriptionMapper;

	@Autowired
	public SubscriptionRestController(SubscriptionService subscriptionService, UserService userService,
			PlanService planService, SubscriptionMapper subscriptionMapper) {
		this.subscriptionService = subscriptionService;
		this.userService = userService;
		this.planService = planService;
		this.subscriptionMapper = subscriptionMapper;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public SubscriptionDto findById(@PathVariable Integer id) {
		Subscription foundSubscription = subscriptionService.findById(id);
		if (foundSubscription == null) {
			throw new EntityNotFoundException("Subscription with this id " + id + " not found");
		}
		return subscriptionMapper.toDto(foundSubscription);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SubscriptionDto create(@Valid @RequestBody SubscriptionDto subscriptionDto) {
		if (!userService.existsById(subscriptionDto.getUserId())) {
			throw new EntityNotFoundException("User with this id " + subscriptionDto.getUserId() + " not found");
		}
		if (!planService.existsById(subscriptionDto.getPlanId())) {
			throw new EntityNotFoundException("Plan with this id " + subscriptionDto.getPlanId() + " not found");
		}
		Subscription createdSubscription = subscriptionService.create(subscriptionMapper.toEntity(subscriptionDto));
		subscriptionDto.setId(createdSubscription.getId());
		return subscriptionDto;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SubscriptionDto update(@PathVariable Integer id, @Valid @RequestBody SubscriptionDto subscriptionDto) {
		if (!subscriptionService.existsById(id)) {
			throw new EntityNotFoundException("Subscription with this id " + id + " not found");
		}
		if (!userService.existsById(subscriptionDto.getUserId())) {
			throw new EntityNotFoundException("User with this id " + subscriptionDto.getUserId() + " not found");
		}
		if (!planService.existsById(subscriptionDto.getPlanId())) {
			throw new EntityNotFoundException("Plan with this id " + subscriptionDto.getPlanId() + " not found");
		}
		subscriptionDto.setId(id);
		subscriptionService.update(subscriptionMapper.toEntity(subscriptionDto));
		return subscriptionDto;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteById(@PathVariable Integer id) {
		if (!subscriptionService.existsById(id)) {
			throw new EntityNotFoundException("Subscription with this id " + id + " not found");
		}
		subscriptionService.deleteById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiPageable
	public List<SubscriptionDto> findAll(@ApiIgnore @PageableDefault(sort = "id") Pageable pageable) {
		return subscriptionMapper.toDto(subscriptionService.findAll(pageable).getContent());
	}

}
