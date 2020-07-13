package ua.ivan909020.api.validators;

import ua.ivan909020.api.annotations.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeValidator implements ConstraintValidator<DateTime, CharSequence> {

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		try {
			LocalDateTime.parse(value);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
