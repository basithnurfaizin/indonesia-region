package io.github.basithnurfaizin.indonesiaregion.loader;

import com.opencsv.CSVReader;
import io.github.basithnurfaizin.indonesiaregion.model.District;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DistrictLoader {

  public static Map<String, District> loadDistricts() {

    Map<String, District> district = new HashMap<>();

    try (CSVReader reader =
        new CSVReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    CityLoader.class.getResourceAsStream("/data/districts.csv"))))) {
      String[] line;
      while ((line = reader.readNext()) != null) {
        if (line[0].equalsIgnoreCase("code")) continue;
        district.put(
            line[0],
                District.builder()
                        .code(line[0])
                        .name(line[2])
                        .cityCode(line[1])
                        .latitude(parseDoubleOrDefault(line[3]))
                        .longitude(parseDoubleOrDefault(line[4]))
                        .build());
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load cities", e);
    }

    return district;
  }

  private static double parseDoubleOrDefault(String value) {
    if (value == null || value.isBlank()) {
      return 0.0;
    }
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }
}
