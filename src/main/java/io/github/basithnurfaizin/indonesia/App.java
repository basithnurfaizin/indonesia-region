package io.github.basithnurfaizin.indonesia;

import io.github.basithnurfaizin.indonesia.model.City;
import io.github.basithnurfaizin.indonesia.model.Province;
import io.github.basithnurfaizin.indonesia.service.IndonesiaService;
import io.github.basithnurfaizin.indonesia.service.IndonesiaServiceImpl;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        IndonesiaService indonesiaService = new IndonesiaServiceImpl();

        List<Province> provinces = indonesiaService.getProvinces("Jawa Barat");

        System.out.printf("provinces "+ provinces.get(0).getName());

        List<City> cities = indonesiaService.getCities(provinces.get(0).getCode(), "");

        System.out.printf("cities "+ cities.toString());


    }
}
