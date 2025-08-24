package io.github.basithnurfaizin.indonesiaregion.service;

import io.github.basithnurfaizin.indonesiaregion.model.City;
import io.github.basithnurfaizin.indonesiaregion.model.District;
import io.github.basithnurfaizin.indonesiaregion.model.Province;
import io.github.basithnurfaizin.indonesiaregion.model.Village;
import java.util.List;

public interface IndonesiaService {
  List<Province> getProvinces(String keyword);

  List<City> getCities(String provinceCode, String keyword);

  List<District> getDistricts(String cityCode, String keyword);

  List<Village> getVillages(String districtCode, String keyword);

  Province getProvince(String provinceCode, List<String> includes);

  City getCity(String cityCode, List<String> includes);

  District getDistrict(String districtCode, List<String> includes);
}
