package com.flip.flipapp.global.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

  private static final String SPLIT_BASE = ", ";

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    return String.join(SPLIT_BASE, attribute);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    if(dbData.isBlank()){
      return List.of();
    }
    return Arrays.asList(dbData.split(SPLIT_BASE));
  }
}
