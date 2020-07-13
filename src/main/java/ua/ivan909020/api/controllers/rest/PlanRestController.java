package ua.ivan909020.api.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import ua.ivan909020.api.annotations.ApiPageable;
import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dto.PlanDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.mappers.PlanMapper;
import ua.ivan909020.api.services.PlanService;

import javax.validation.Valid;
import java.util.List;

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
	@ApiPageable
	public List<PlanDto> findAll(@ApiIgnore @PageableDefault(sort = "id") Pageable pageable) {
		return planMapper.toDto(planService.findAll(pageable).getContent());
	}

}
