package io.github.basithnurfaizin.indonesia.loader;

import com.opencsv.CSVReader;
import io.github.basithnurfaizin.indonesia.Province;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProvinceLoader {

    public static Map<String, Province> loadProvinces() {

        Map<String, Province> provinces = new HashMap<>();

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(Objects.requireNonNull(ProvinceLoader.class.getResourceAsStream("/data/provinces.csv"))))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[0].equalsIgnoreCase("code")) continue;
                provinces.put(line[0], new Province(line[0], line[1],
                        Double.parseDouble(line[2]), Double.parseDouble(line[3])));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load provinces", e);
        }

        return provinces;
    }
}
