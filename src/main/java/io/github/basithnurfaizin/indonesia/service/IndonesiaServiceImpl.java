package io.github.basithnurfaizin.indonesia.service;


import io.github.basithnurfaizin.indonesia.Province;
import io.github.basithnurfaizin.indonesia.loader.ProvinceLoader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class IndonesiaServiceImpl implements IndonesiaService {

    private final Map<String, Province> provinces;

    public IndonesiaServiceImpl() {
        this.provinces = ProvinceLoader.loadProvinces();
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

}
