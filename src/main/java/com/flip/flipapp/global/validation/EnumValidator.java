package com.flip.flipapp.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

  private EnumValue enumValue;

  @Override
  public void initialize(EnumValue constraintAnnotation) {
    this.enumValue = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Enum<?>[] enumConstants = this.enumValue.target().getEnumConstants();

    if (enumConstants != null) {
      for (Object enumConstant : enumConstants) {
        if (enumConstant.toString().equals(value)) {
          return true;
        }
      }
    }

    context
        .buildConstraintViolationWithTemplate(
            String.format("must be any of %s", Arrays.toString(enumConstants))
        )
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
    return false;
  }
}
