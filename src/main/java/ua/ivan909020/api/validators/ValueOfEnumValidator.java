package ua.ivan909020.api.validators;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

	private List<String> enumValues;

	@Override
	public void initialize(ValueOfEnum annotation) {
		this.enumValues = Stream.of(annotation.enumClass().getEnumConstants())
				.map(Enum::name)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return this.enumValues.contains(value.toString());
	}

}
