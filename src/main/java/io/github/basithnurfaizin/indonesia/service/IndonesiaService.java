package io.github.basithnurfaizin.indonesia.service;

import io.github.basithnurfaizin.indonesia.Province;

import java.util.List;

public interface IndonesiaService {
    List<Province> getProvinces(String keyword);
}
