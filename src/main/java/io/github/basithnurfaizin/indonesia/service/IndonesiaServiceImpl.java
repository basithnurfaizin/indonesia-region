package io.github.basithnurfaizin.indonesia.service;


import io.github.basithnurfaizin.indonesia.loader.CityLoader;
import io.github.basithnurfaizin.indonesia.model.City;
import io.github.basithnurfaizin.indonesia.model.Province;
import io.github.basithnurfaizin.indonesia.loader.ProvinceLoader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class IndonesiaServiceImpl implements IndonesiaService {

    private final Map<String, Province> provinces;
    private final Map<String, City> cities;

    public IndonesiaServiceImpl() {
        this.provinces = ProvinceLoader.loadProvinces();
        this.cities = CityLoader.loadCities();
    }

    @Override
    public List<Province> getProvinces(String keyword) {
        Stream<Province> stream = provinces.values().stream();

        if (keyword != null && !keyword.isBlank()) {
            String lower = keyword.toLowerCase();

            stream = stream.filter(p ->
                    p.getCode().equalsIgnoreCase(keyword) ||   // match by code
                            p.getName().toLowerCase().contains(lower)  // match by name (partial)
            );
        }

        return stream
                .sorted(Comparator.comparing(Province::getCode)) // sort by code
                .toList();
    }

    @Override
    public List<City> getCities(String provinceCode, String keyword) {
        Stream<City> stream = cities.values().stream();

        // filter by provinceCode
        if (provinceCode != null && !provinceCode.isBlank()) {
            stream = stream.filter(city -> city.getProvinceCode().equalsIgnoreCase(provinceCode));
        }

        // filter by keyword (search in code or name)
        if (keyword != null && !keyword.isBlank()) {
            String lowerKeyword = keyword.toLowerCase();
            stream = stream.filter(city ->
                    city.getCode().toLowerCase().contains(lowerKeyword) ||
                            city.getName().toLowerCase().contains(lowerKeyword));
        }

        return stream
                .sorted(Comparator.comparing(City::getCode)) // sort by code
                .toList();
    }

}
