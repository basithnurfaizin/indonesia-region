package io.github.basithnurfaizin.indonesiaregion.loader;

import com.opencsv.CSVReader;
import io.github.basithnurfaizin.indonesiaregion.model.City;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CityLoader {

  public static Map<String, City> loadCities() {

    Map<String, City> cities = new HashMap<>();

    try (CSVReader reader =
        new CSVReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    CityLoader.class.getResourceAsStream("/data/cities.csv"))))) {
      String[] line;
      while ((line = reader.readNext()) != null) {
        if (line[0].equalsIgnoreCase("code")) continue;
        cities.put(
            line[0],
                City.builder()
                        .code(line[0])
                        .name(line[2])
                        .provinceCode(line[1])
                        .latitude(Double.parseDouble(line[3]))
                        .longitude(Double.parseDouble(line[4]))
                        .build());
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load cities", e);
    }

    return cities;
  }
}
