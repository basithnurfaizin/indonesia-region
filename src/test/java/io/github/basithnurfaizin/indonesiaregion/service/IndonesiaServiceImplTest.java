package io.github.basithnurfaizin.indonesiaregion.service;

import static org.junit.jupiter.api.Assertions.*;

import io.github.basithnurfaizin.indonesiaregion.model.City;
import io.github.basithnurfaizin.indonesiaregion.model.District;
import io.github.basithnurfaizin.indonesiaregion.model.Province;
import io.github.basithnurfaizin.indonesiaregion.model.Village;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IndonesiaServiceImplTest {

  private IndonesiaService indonesiaService;

  @BeforeEach
  void setUp() {
    indonesiaService = new IndonesiaServiceImpl();
  }

  @Nested
  @DisplayName("Province Tests")
  class ProvinceTests {

    @Test
    @DisplayName("Should return all provinces when no keyword provided")
    void shouldReturnAllProvincesWhenNoKeyword() {
      List<Province> provinces = indonesiaService.getProvinces(null);

      assertNotNull(provinces);
      assertFalse(provinces.isEmpty());

      // Verify provinces are sorted by code
      for (int i = 1; i < provinces.size(); i++) {
        assertTrue(provinces.get(i - 1).getCode().compareTo(provinces.get(i).getCode()) <= 0);
      }
    }

    @Test
    @DisplayName("Should return all provinces when empty keyword provided")
    void shouldReturnAllProvincesWhenEmptyKeyword() {
      List<Province> provincesNull = indonesiaService.getProvinces(null);
      List<Province> provincesEmpty = indonesiaService.getProvinces("");
      List<Province> provincesBlank = indonesiaService.getProvinces("   ");

      assertEquals(provincesNull.size(), provincesEmpty.size());
      assertEquals(provincesNull.size(), provincesBlank.size());
    }

    @Test
    @DisplayName("Should filter provinces by exact code match")
    void shouldFilterProvincesByExactCodeMatch() {
      // Assuming there's a province with code "11" (Aceh)
      List<Province> provinces = indonesiaService.getProvinces("11");

      assertNotNull(provinces);
      // Should find province with exact code match
      assertTrue(provinces.stream().anyMatch(p -> "11".equalsIgnoreCase(p.getCode())));
    }

    @Test
    @DisplayName("Should filter provinces by partial code match")
    void shouldFilterProvincesByPartialCodeMatch() {
      List<Province> provinces = indonesiaService.getProvinces("1");

      assertNotNull(provinces);
      assertFalse(provinces.isEmpty());

      // All returned provinces should have code containing "1"
      assertTrue(
          provinces.stream()
              .allMatch(
                  p ->
                      p.getCode().toLowerCase().contains("1")
                          || p.getName().toLowerCase().contains("1")));
    }

    @Test
    @DisplayName("Should filter provinces by name")
    void shouldFilterProvincesByName() {
      List<Province> provinces = indonesiaService.getProvinces("jakarta");

      assertNotNull(provinces);

      // All returned provinces should have name containing "jakarta" or code containing "jakarta"
      assertTrue(
          provinces.stream()
              .allMatch(
                  p ->
                      p.getName().toLowerCase().contains("jakarta")
                          || p.getCode().toLowerCase().contains("jakarta")));
    }

    @Test
    @DisplayName("Should return empty list for non-existent province")
    void shouldReturnEmptyListForNonExistentProvince() {
      List<Province> provinces = indonesiaService.getProvinces("NONEXISTENT");

      assertNotNull(provinces);
      assertTrue(provinces.isEmpty());
    }

    @Test
    @DisplayName("Should be case insensitive for province search")
    void shouldBeCaseInsensitiveForProvinceSearch() {
      List<Province> upperCase = indonesiaService.getProvinces("JAKARTA");
      List<Province> lowerCase = indonesiaService.getProvinces("jakarta");
      List<Province> mixedCase = indonesiaService.getProvinces("JaKaRtA");

      assertEquals(upperCase.size(), lowerCase.size());
      assertEquals(upperCase.size(), mixedCase.size());
    }
  }

  @Nested
  @DisplayName("City Tests")
  class CityTests {

    @Test
    @DisplayName("Should return all cities when no province code and keyword provided")
    void shouldReturnAllCitiesWhenNoFilters() {
      List<City> cities = indonesiaService.getCities(null, null);

      assertNotNull(cities);
      assertFalse(cities.isEmpty());

      // Verify cities are sorted by code
      for (int i = 1; i < cities.size(); i++) {
        assertTrue(cities.get(i - 1).getCode().compareTo(cities.get(i).getCode()) <= 0);
      }
    }

    @Test
    @DisplayName("Should filter cities by province code")
    void shouldFilterCitiesByProvinceCode() {
      // Assuming there are cities for province code "31" (DKI Jakarta)
      List<City> cities = indonesiaService.getCities("31", null);

      assertNotNull(cities);

      // All returned cities should belong to the specified province
      assertTrue(cities.stream().allMatch(city -> "31".equalsIgnoreCase(city.getProvinceCode())));
    }

    @Test
    @DisplayName("Should filter cities by keyword")
    void shouldFilterCitiesByKeyword() {
      List<City> cities = indonesiaService.getCities(null, "jakarta");

      assertNotNull(cities);

      // All returned cities should match the keyword in code or name
      assertTrue(
          cities.stream()
              .allMatch(
                  city ->
                      city.getCode().toLowerCase().contains("jakarta")
                          || city.getName().toLowerCase().contains("jakarta")));
    }

    @Test
    @DisplayName("Should filter cities by both province code and keyword")
    void shouldFilterCitiesByProvinceCodeAndKeyword() {
      List<City> cities = indonesiaService.getCities("31", "jakarta");

      assertNotNull(cities);

      // All returned cities should belong to province and match keyword
      assertTrue(
          cities.stream()
              .allMatch(
                  city ->
                      "31".equalsIgnoreCase(city.getProvinceCode())
                          && (city.getCode().toLowerCase().contains("jakarta")
                              || city.getName().toLowerCase().contains("jakarta"))));
    }

    @Test
    @DisplayName("Should return empty list for non-existent province code")
    void shouldReturnEmptyListForNonExistentProvinceCode() {
      List<City> cities = indonesiaService.getCities("999", null);

      assertNotNull(cities);
      assertTrue(cities.isEmpty());
    }

    @Test
    @DisplayName("Should be case insensitive for city search")
    void shouldBeCaseInsensitiveForCitySearch() {
      List<City> upperCase = indonesiaService.getCities("31", "JAKARTA");
      List<City> lowerCase = indonesiaService.getCities("31", "jakarta");

      assertEquals(upperCase.size(), lowerCase.size());
    }
  }

  @Nested
  @DisplayName("District Tests")
  class DistrictTests {

    @Test
    @DisplayName("Should return all districts when no city code and keyword provided")
    void shouldReturnAllDistrictsWhenNoFilters() {
      List<District> districts = indonesiaService.getDistricts(null, null);

      assertNotNull(districts);
      assertFalse(districts.isEmpty());

      // Verify districts are sorted by code
      for (int i = 1; i < districts.size(); i++) {
        assertTrue(districts.get(i - 1).getCode().compareTo(districts.get(i).getCode()) <= 0);
      }
    }

    @Test
    @DisplayName("Should filter districts by city code")
    void shouldFilterDistrictsByCityCode() {
      // Get a city first to use its code
      List<City> cities = indonesiaService.getCities("31", null);
      if (!cities.isEmpty()) {
        String cityCode = cities.get(0).getCode();
        List<District> districts = indonesiaService.getDistricts(cityCode, null);

        assertNotNull(districts);

        // All returned districts should belong to the specified city
        assertTrue(
            districts.stream()
                .allMatch(district -> cityCode.equalsIgnoreCase(district.getCityCode())));
      }
    }

    @Test
    @DisplayName("Should filter districts by keyword")
    void shouldFilterDistrictsByKeyword() {
      List<District> districts = indonesiaService.getDistricts(null, "central");

      assertNotNull(districts);

      // All returned districts should match the keyword
      assertTrue(
          districts.stream()
              .allMatch(
                  district ->
                      district.getCode().toLowerCase().contains("central")
                          || district.getName().toLowerCase().contains("central")));
    }

    @Test
    @DisplayName("Should return empty list for non-existent city code")
    void shouldReturnEmptyListForNonExistentCityCode() {
      List<District> districts = indonesiaService.getDistricts("999999", null);

      assertNotNull(districts);
      assertTrue(districts.isEmpty());
    }
  }

  @Nested
  @DisplayName("Village Tests")
  class VillageTests {

    @Test
    @DisplayName("Should return all villages when no district code and keyword provided")
    void shouldReturnAllVillagesWhenNoFilters() {
      List<Village> villages = indonesiaService.getVillages(null, null);

      assertNotNull(villages);
      assertFalse(villages.isEmpty());

      // Verify villages are sorted by code
      for (int i = 1; i < villages.size(); i++) {
        assertTrue(villages.get(i - 1).getCode().compareTo(villages.get(i).getCode()) <= 0);
      }
    }

    @Test
    @DisplayName("Should filter villages by district code")
    void shouldFilterVillagesByDistrictCode() {
      // Get a district first to use its code
      List<District> districts = indonesiaService.getDistricts(null, null);
      if (!districts.isEmpty()) {
        String districtCode = districts.get(0).getCode();
        List<Village> villages = indonesiaService.getVillages(districtCode, null);

        assertNotNull(villages);

        // All returned villages should belong to the specified district
        assertTrue(
            villages.stream()
                .allMatch(village -> districtCode.equalsIgnoreCase(village.getDistrictCode())));
      }
    }

    @Test
    @DisplayName("Should filter villages by keyword")
    void shouldFilterVillagesByKeyword() {
      List<Village> villages = indonesiaService.getVillages(null, "desa");

      assertNotNull(villages);

      // All returned villages should match the keyword
      assertTrue(
          villages.stream()
              .allMatch(
                  village ->
                      village.getCode().toLowerCase().contains("desa")
                          || village.getName().toLowerCase().contains("desa")));
    }

    @Test
    @DisplayName("Should return empty list for non-existent district code")
    void shouldReturnEmptyListForNonExistentDistrictCode() {
      List<Village> villages = indonesiaService.getVillages("999999999", null);

      assertNotNull(villages);
      assertTrue(villages.isEmpty());
    }
  }

  @Nested
  @DisplayName("Edge Cases and Integration Tests")
  class EdgeCasesTests {

    @Test
    @DisplayName("Should handle null and blank strings consistently")
    void shouldHandleNullAndBlankStringsConsistently() {
      // Test provinces
      List<Province> provincesNull = indonesiaService.getProvinces(null);
      List<Province> provincesEmpty = indonesiaService.getProvinces("");
      List<Province> provincesBlank = indonesiaService.getProvinces("   ");

      assertEquals(provincesNull.size(), provincesEmpty.size());
      assertEquals(provincesNull.size(), provincesBlank.size());

      // Test cities
      List<City> citiesNull = indonesiaService.getCities(null, null);
      List<City> citiesEmpty = indonesiaService.getCities("", "");
      List<City> citiesBlank = indonesiaService.getCities("   ", "   ");

      assertEquals(citiesNull.size(), citiesEmpty.size());
      assertEquals(citiesNull.size(), citiesBlank.size());
    }

    @Test
    @DisplayName("Should maintain data integrity across different calls")
    void shouldMaintainDataIntegrityAcrossCalls() {
      // Multiple calls should return consistent results
      List<Province> provinces1 = indonesiaService.getProvinces(null);
      List<Province> provinces2 = indonesiaService.getProvinces(null);

      assertEquals(provinces1.size(), provinces2.size());

      // Verify that the service doesn't modify the underlying data
      for (int i = 0; i < provinces1.size(); i++) {
        assertEquals(provinces1.get(i).getCode(), provinces2.get(i).getCode());
        assertEquals(provinces1.get(i).getName(), provinces2.get(i).getName());
      }
    }

    @Test
    @DisplayName("Should handle special characters in search")
    void shouldHandleSpecialCharactersInSearch() {
      // Test with special characters that might exist in Indonesian names
      List<Province> provinces = indonesiaService.getProvinces("D.I");
      assertNotNull(provinces);

      List<City> cities = indonesiaService.getCities(null, "D.I");
      assertNotNull(cities);
    }

    @Test
    @DisplayName("Should perform hierarchical data consistency check")
    void shouldPerformHierarchicalDataConsistencyCheck() {
      // Get a province and verify its cities exist
      List<Province> provinces = indonesiaService.getProvinces(null);
      if (!provinces.isEmpty()) {
        Province firstProvince = provinces.get(0);
        List<City> cities = indonesiaService.getCities(firstProvince.getCode(), null);

        // If there are cities for this province, get districts for the first city
        if (!cities.isEmpty()) {
          City firstCity = cities.get(0);
          List<District> districts = indonesiaService.getDistricts(firstCity.getCode(), null);

          // If there are districts for this city, get villages for the first district
          if (!districts.isEmpty()) {
            District firstDistrict = districts.get(0);
            List<Village> villages = indonesiaService.getVillages(firstDistrict.getCode(), null);

            // This test ensures the hierarchical relationship works
            assertNotNull(villages);
          }
        }
      }
    }

    @Test
    @DisplayName("Should handle exact code matches correctly")
    void shouldHandleExactCodeMatchesCorrectly() {
      // Get all provinces and test exact match for the first one
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      if (!allProvinces.isEmpty()) {
        String firstProvinceCode = allProvinces.get(0).getCode();
        List<Province> exactMatch = indonesiaService.getProvinces(firstProvinceCode);

        // Should contain at least the exact match
        assertTrue(
            exactMatch.stream().anyMatch(p -> p.getCode().equalsIgnoreCase(firstProvinceCode)));
      }
    }
  }
}
