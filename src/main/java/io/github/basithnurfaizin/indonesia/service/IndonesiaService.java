package io.github.basithnurfaizin.indonesia.service;

import io.github.basithnurfaizin.indonesia.model.City;
import io.github.basithnurfaizin.indonesia.model.Province;

import java.util.List;

public interface IndonesiaService {
    List<Province> getProvinces(String keyword);

    List<City> getCities(String provinceCode, String keyword);
}
