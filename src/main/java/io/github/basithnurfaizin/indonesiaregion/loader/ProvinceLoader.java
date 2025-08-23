package io.github.basithnurfaizin.indonesiaregion.loader;

import com.opencsv.CSVReader;
import io.github.basithnurfaizin.indonesiaregion.model.Province;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProvinceLoader {

  public static Map<String, Province> loadProvinces() {

    Map<String, Province> provinces = new HashMap<>();

    try (CSVReader reader =
        new CSVReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    ProvinceLoader.class.getResourceAsStream("/data/provinces.csv"))))) {
      String[] line;
      while ((line = reader.readNext()) != null) {
        if (line[0].equalsIgnoreCase("code")) continue;
        provinces.put(
            line[0],
            Province.builder()
                .code(line[0])
                .name(line[1])
                .latitude(Double.parseDouble(line[2]))
                .longitude(Double.parseDouble(line[3]))
                .build());
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load provinces", e);
    }

    return provinces;
  }
}
