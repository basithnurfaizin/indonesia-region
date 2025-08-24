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
    long startTime = System.nanoTime(); // start timer
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

    Province province = indonesiaService.getProvince("32", List.of("cities", "districts", "villages"));

    System.out.println("province : " + province.getName());
    System.out.println(province.getCities().size());
    System.out.println(province.getCities().get(0).getDistricts().size());
    System.out.println(province.getCities().get(0).getDistricts().get(0).getVillages().size());


    long endTime = System.nanoTime(); // end timer

    long durationInNanoseconds = endTime - startTime;
    double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;

    System.out.println("Execution time: " + durationInMilliseconds + " ms");

  }
}
