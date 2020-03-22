package ua.ivan909020.api.controllers.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dto.PlanDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.mappers.PlanMapper;
import ua.ivan909020.api.services.PlanService;

@RestController
@RequestMapping("/plans")
public class PlanRestController {

	private final PlanService planService;
	private final PlanMapper planMapper;

	@Autowired
	public PlanRestController(PlanService planService, PlanMapper planMapper) {
		this.planService = planService;
		this.planMapper = planMapper;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PlanDto findById(@PathVariable Integer id) {
		Plan foundPlan = planService.findById(id);
		if (foundPlan == null) {
			throw new EntityNotFoundException("Plan with this id " + id + " not found");
		}
		return planMapper.toDto(foundPlan);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PlanDto create(@Valid @RequestBody PlanDto planDto) {
		Plan createdPlan = planService.create(planMapper.toEntity(planDto));
		planDto.setId(createdPlan.getId());
		return planDto;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public PlanDto update(@PathVariable Integer id, @Valid @RequestBody PlanDto planDto) {
		if (!planService.existsById(id)) {
			throw new EntityNotFoundException("Plan with this id " + id + " not found");
		}
		planDto.setId(id);
		planService.update(planMapper.toEntity(planDto));
		return planDto;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void delete(@PathVariable Integer id) {
		if (!planService.existsById(id)) {
			throw new EntityNotFoundException("Plan with this id " + id + " not found");
		}
		planService.deleteById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PlanDto> findAll(@RequestParam Integer page, @RequestParam Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return planMapper.toDto(planService.findAll(pageable).getContent());
	}

}
