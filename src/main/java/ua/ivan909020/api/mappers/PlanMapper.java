package ua.ivan909020.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dto.PlanDto;

@Mapper(componentModel = "spring")
public interface PlanMapper {

	PlanDto toDto(Plan plan);

	Plan toEntity(PlanDto planDto);

	List<PlanDto> toDto(List<Plan> plans);

}
