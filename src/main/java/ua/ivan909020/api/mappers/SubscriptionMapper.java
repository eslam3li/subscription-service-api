package ua.ivan909020.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.ivan909020.api.domain.dao.Subscription;
import ua.ivan909020.api.domain.dto.SubscriptionDto;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "plan.id", target = "planId")
	SubscriptionDto toDto(Subscription subscription);

	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "planId", target = "plan.id")
	Subscription toEntity(SubscriptionDto subscriptionDto);

	List<SubscriptionDto> toDto(List<Subscription> subscriptions);

}
