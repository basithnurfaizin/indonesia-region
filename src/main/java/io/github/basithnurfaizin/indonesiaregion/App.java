package io.github.basithnurfaizin.indonesiaregion;

import io.github.basithnurfaizin.indonesiaregion.model.City;
import io.github.basithnurfaizin.indonesiaregion.model.District;
import io.github.basithnurfaizin.indonesiaregion.model.Province;
import io.github.basithnurfaizin.indonesiaregion.model.Village;
import io.github.basithnurfaizin.indonesiaregion.service.IndonesiaService;
import io.github.basithnurfaizin.indonesiaregion.service.IndonesiaServiceImpl;
import java.util.List;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    IndonesiaService indonesiaService = new IndonesiaServiceImpl();

    List<Province> provinces = indonesiaService.getProvinces("Jawa Barat");

    System.out.printf("provinces " + provinces.get(0).getName());

    List<City> cities = indonesiaService.getCities(provinces.get(0).getCode(), "");

    System.out.println("cities " + cities.size());

    System.out.println(cities.get(0).getCode());

    List<District> districts = indonesiaService.getDistricts(cities.get(0).getCode(), "");

    System.out.println("districts : " + districts.size());

    List<Village> villages = indonesiaService.getVillages(districts.get(0).getCode(), "");

    System.out.println("villages " + villages.size());
  }
}
