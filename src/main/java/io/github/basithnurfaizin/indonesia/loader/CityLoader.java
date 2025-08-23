package io.github.basithnurfaizin.indonesia.loader;

import com.opencsv.CSVReader;
import io.github.basithnurfaizin.indonesia.model.City;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CityLoader {

    public static Map<String, City> loadCities() {

        Map<String, City> cities = new HashMap<>();

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(Objects.requireNonNull(CityLoader.class.getResourceAsStream("/data/cities.csv"))))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[0].equalsIgnoreCase("code")) continue;
                cities.put(line[0], new City(line[0], line[1], line[2],
                        Double.parseDouble(line[3]), Double.parseDouble(line[4])));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cities", e);
        }

        return cities;
    }
}
