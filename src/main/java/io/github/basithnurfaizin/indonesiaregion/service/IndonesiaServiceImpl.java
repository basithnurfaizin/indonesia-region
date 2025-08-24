package io.github.basithnurfaizin.indonesiaregion.service;

import io.github.basithnurfaizin.indonesiaregion.loader.CityLoader;
import io.github.basithnurfaizin.indonesiaregion.loader.DistrictLoader;
import io.github.basithnurfaizin.indonesiaregion.loader.ProvinceLoader;
import io.github.basithnurfaizin.indonesiaregion.loader.VillageLoader;
import io.github.basithnurfaizin.indonesiaregion.model.City;
import io.github.basithnurfaizin.indonesiaregion.model.District;
import io.github.basithnurfaizin.indonesiaregion.model.Province;
import io.github.basithnurfaizin.indonesiaregion.model.Village;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class IndonesiaServiceImpl implements IndonesiaService {

  private final Map<String, Province> provinces;
  private final Map<String, City> cities;
  private final Map<String, District> districts;
  private final Map<String, Village> villages;

  public IndonesiaServiceImpl() {
    this.provinces = ProvinceLoader.loadProvinces();
    this.cities = CityLoader.loadCities();
    this.districts = DistrictLoader.loadDistricts();
    this.villages = VillageLoader.loadVillages();
  }

  @Override
  public List<Province> getProvinces(String keyword) {
    return filterAndSort(
        provinces.values().stream(),
        keyword,
        Province::getCode,
        Province::getName,
        Province::getCode);
  }

  @Override
  public List<City> getCities(String provinceCode, String keyword) {
    Stream<City> stream = cities.values().stream();
    if (isNotBlank(provinceCode)) {
      stream = stream.filter(city -> city.getProvinceCode().equalsIgnoreCase(provinceCode));
    }
    return filterAndSort(stream, keyword, City::getCode, City::getName, City::getCode);
  }

  @Override
  public List<District> getDistricts(String cityCode, String keyword) {
    Stream<District> stream = districts.values().stream();
    if (isNotBlank(cityCode)) {
      stream = stream.filter(d -> d.getCityCode().equalsIgnoreCase(cityCode));
    }
    return filterAndSort(stream, keyword, District::getCode, District::getName, District::getCode);
  }

  @Override
  public List<Village> getVillages(String districtCode, String keyword) {
    Stream<Village> stream = villages.values().stream();
    if (isNotBlank(districtCode)) {
      stream = stream.filter(v -> v.getDistrictCode().equalsIgnoreCase(districtCode));
    }
    return filterAndSort(stream, keyword, Village::getCode, Village::getName, Village::getCode);
  }

  @Override
  public Province getProvince(String provinceCode, List<String> includes) {

    Province province = provinces.get(provinceCode);

    if (province == null) {
      return null;
    }

    Province result = new Province();
    result.setCode(province.getCode());
    result.setName(province.getName());
    result.setLatitude(province.getLatitude());
    result.setLongitude(province.getLongitude());


    if (includes != null && includes.contains("cities")) {
      List<City> cities = getCities(province.getCode(), null);

      if (includes.contains("districts")) {
        cities.parallelStream().forEach(city -> {
          List<District> districts = getDistricts(city.getCode(), null);

          if (includes.contains("villages")) {
            districts.parallelStream().forEach(district -> {
              List<Village> villages = getVillages(district.getCode(), null);
              district.setVillages(villages);
            });
          }

          city.setDistricts(districts);
        });
      }

      result.setCities(cities);
    }

    return result;
  }

  private static boolean isNotBlank(String str) {
    return str != null && !str.isBlank();
  }

  private static <T> List<T> filterAndSort(
      Stream<T> stream,
      String keyword,
      Function<T, String> codeExtractor,
      Function<T, String> nameExtractor,
      Function<T, String> sortKey) {
    if (isNotBlank(keyword)) {
      String lower = keyword.toLowerCase();
      stream =
          stream.filter(
              item ->
                  codeExtractor.apply(item).equalsIgnoreCase(keyword)
                      || codeExtractor.apply(item).toLowerCase().contains(lower)
                      || nameExtractor.apply(item).toLowerCase().contains(lower));
    }
    return stream.sorted(Comparator.comparing(sortKey)).toList();
  }
}
