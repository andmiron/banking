package com.example.banking.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;
import java.util.Set;

public class IsoCountryValidator implements ConstraintValidator<IsoCountry, String> {

    private static final Set<String> ISO_CODES = Set.of(Locale.getISOCountries());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return ISO_CODES.contains(value.toUpperCase(Locale.ROOT));
    }
}
