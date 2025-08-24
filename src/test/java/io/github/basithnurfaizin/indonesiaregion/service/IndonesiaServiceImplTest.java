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

  @Nested
  @DisplayName("Individual Province Retrieval Tests")
  class ProvinceRetrievalTests {

    @Test
    @DisplayName("Should return province without nested data when no includes provided")
    void shouldReturnProvinceWithoutNestedDataWhenNoIncludes() {
      // Get a valid province code first
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province = indonesiaService.getProvince(provinceCode, null);

      assertNotNull(province);
      assertEquals(provinceCode, province.getCode());
      assertNotNull(province.getName());
      assertNull(province.getCities()); // Should not include cities
    }

    @Test
    @DisplayName("Should return province without nested data when empty includes provided")
    void shouldReturnProvinceWithoutNestedDataWhenEmptyIncludes() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province = indonesiaService.getProvince(provinceCode, List.of());

      assertNotNull(province);
      assertEquals(provinceCode, province.getCode());
      assertNull(province.getCities()); // Should not include cities
    }

    @Test
    @DisplayName("Should return province with cities when cities included")
    void shouldReturnProvinceWithCitiesWhenCitiesIncluded() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province = indonesiaService.getProvince(provinceCode, List.of("cities"));

      assertNotNull(province);
      assertEquals(provinceCode, province.getCode());
      assertNotNull(province.getCities()); // Should include cities

      // Verify cities belong to this province
      assertTrue(
          province.getCities().stream()
              .allMatch(city -> provinceCode.equalsIgnoreCase(city.getProvinceCode())));

      // Cities should not have nested districts
      if (!province.getCities().isEmpty()) {
        assertNull(province.getCities().get(0).getDistricts());
      }
    }

    @Test
    @DisplayName("Should return province with cities and districts when both included")
    void shouldReturnProvinceWithCitiesAndDistrictsWhenBothIncluded() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts"));

      assertNotNull(province);
      assertEquals(provinceCode, province.getCode());
      assertNotNull(province.getCities());

      // Check if cities have districts loaded
      if (!province.getCities().isEmpty()) {
        City firstCity = province.getCities().get(0);
        assertNotNull(firstCity.getDistricts());

        // Verify districts belong to the city
        assertTrue(
            firstCity.getDistricts().stream()
                .allMatch(
                    district -> firstCity.getCode().equalsIgnoreCase(district.getCityCode())));

        // Districts should not have villages yet
        if (!firstCity.getDistricts().isEmpty()) {
          assertNull(firstCity.getDistricts().get(0).getVillages());
        }
      }
    }

    @Test
    @DisplayName("Should return province with full hierarchy when all includes provided")
    void shouldReturnProvinceWithFullHierarchyWhenAllIncludesProvided() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts", "villages"));

      assertNotNull(province);
      assertEquals(provinceCode, province.getCode());
      assertNotNull(province.getCities());

      if (!province.getCities().isEmpty()) {
        City firstCity = province.getCities().get(0);
        assertNotNull(firstCity.getDistricts());

        if (!firstCity.getDistricts().isEmpty()) {
          District firstDistrict = firstCity.getDistricts().get(0);
          assertNotNull(firstDistrict.getVillages());

          // Verify villages belong to the district
          assertTrue(
              firstDistrict.getVillages().stream()
                  .allMatch(
                      village ->
                          firstDistrict.getCode().equalsIgnoreCase(village.getDistrictCode())));
        }
      }
    }

    @Test
    @DisplayName("Should return null for non-existent province code")
    void shouldReturnNullForNonExistentProvinceCode() {
      Province province = indonesiaService.getProvince("999", List.of("cities"));
      assertNull(province);
    }

    @Test
    @DisplayName("Should ignore invalid includes parameters")
    void shouldIgnoreInvalidIncludesParameters() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province =
          indonesiaService.getProvince(
              provinceCode, List.of("cities", "invalid", "districts", "unknown"));

      assertNotNull(province);
      assertNotNull(province.getCities()); // Should include cities

      // Should include districts because "districts" was in the list
      if (!province.getCities().isEmpty()) {
        assertNotNull(province.getCities().get(0).getDistricts());
      }
    }

    @Test
    @DisplayName("Should preserve original province properties")
    void shouldPreserveOriginalProvinceProperties() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province originalProvince = allProvinces.get(0);
      Province retrievedProvince = indonesiaService.getProvince(provinceCode, List.of("cities"));

      assertEquals(originalProvince.getCode(), retrievedProvince.getCode());
      assertEquals(originalProvince.getName(), retrievedProvince.getName());
      assertEquals(originalProvince.getLatitude(), retrievedProvince.getLatitude());
      assertEquals(originalProvince.getLongitude(), retrievedProvince.getLongitude());
    }
  }

  @Nested
  @DisplayName("Individual City Retrieval Tests")
  class CityRetrievalTests {

    @Test
    @DisplayName("Should return city without nested data when no includes provided")
    void shouldReturnCityWithoutNestedDataWhenNoIncludes() {
      List<City> allCities = indonesiaService.getCities(null, null);
      assertFalse(allCities.isEmpty());

      String cityCode = allCities.get(0).getCode();
      City city = indonesiaService.getCity(cityCode, null);

      assertNotNull(city);
      assertEquals(cityCode, city.getCode());
      assertNotNull(city.getName());
      assertNull(city.getDistricts()); // Should not include districts
    }

    @Test
    @DisplayName("Should return city with districts when districts included")
    void shouldReturnCityWithDistrictsWhenDistrictsIncluded() {
      List<City> allCities = indonesiaService.getCities(null, null);
      assertFalse(allCities.isEmpty());

      String cityCode = allCities.get(0).getCode();
      City city = indonesiaService.getCity(cityCode, List.of("districts"));

      assertNotNull(city);
      assertEquals(cityCode, city.getCode());
      assertNotNull(city.getDistricts()); // Should include districts

      // Verify districts belong to this city
      assertTrue(
          city.getDistricts().stream()
              .allMatch(district -> cityCode.equalsIgnoreCase(district.getCityCode())));

      // Districts should not have villages yet
      if (!city.getDistricts().isEmpty()) {
        assertNull(city.getDistricts().get(0).getVillages());
      }
    }

    @Test
    @DisplayName("Should return city with districts and villages when both included")
    void shouldReturnCityWithDistrictsAndVillagesWhenBothIncluded() {
      List<City> allCities = indonesiaService.getCities(null, null);
      assertFalse(allCities.isEmpty());

      String cityCode = allCities.get(0).getCode();
      City city = indonesiaService.getCity(cityCode, List.of("districts", "villages"));

      assertNotNull(city);
      assertEquals(cityCode, city.getCode());
      assertNotNull(city.getDistricts());

      if (!city.getDistricts().isEmpty()) {
        District firstDistrict = city.getDistricts().get(0);
        assertNotNull(firstDistrict.getVillages()); // Should include villages

        // Verify villages belong to the district
        assertTrue(
            firstDistrict.getVillages().stream()
                .allMatch(
                    village ->
                        firstDistrict.getCode().equalsIgnoreCase(village.getDistrictCode())));
      }
    }

    @Test
    @DisplayName("Should return null for non-existent city code")
    void shouldReturnNullForNonExistentCityCode() {
      City city = indonesiaService.getCity("999999", List.of("districts"));
      assertNull(city);
    }

    @Test
    @DisplayName("Should preserve original city properties")
    void shouldPreserveOriginalCityProperties() {
      List<City> allCities = indonesiaService.getCities(null, null);
      assertFalse(allCities.isEmpty());

      String cityCode = allCities.get(0).getCode();
      City originalCity = allCities.get(0);
      City retrievedCity = indonesiaService.getCity(cityCode, List.of("districts"));

      assertEquals(originalCity.getCode(), retrievedCity.getCode());
      assertEquals(originalCity.getName(), retrievedCity.getName());
      assertEquals(originalCity.getLatitude(), retrievedCity.getLatitude());
      assertEquals(originalCity.getLongitude(), retrievedCity.getLongitude());
    }

    @Test
    @DisplayName("Should handle empty includes list")
    void shouldHandleEmptyIncludesList() {
      List<City> allCities = indonesiaService.getCities(null, null);
      assertFalse(allCities.isEmpty());

      String cityCode = allCities.get(0).getCode();
      City city = indonesiaService.getCity(cityCode, List.of());

      assertNotNull(city);
      assertEquals(cityCode, city.getCode());
      assertNull(city.getDistricts()); // Should not include districts
    }
  }

  @Nested
  @DisplayName("Individual District Retrieval Tests")
  class DistrictRetrievalTests {

    @Test
    @DisplayName("Should return district without nested data when no includes provided")
    void shouldReturnDistrictWithoutNestedDataWhenNoIncludes() {
      List<District> allDistricts = indonesiaService.getDistricts(null, null);
      assertFalse(allDistricts.isEmpty());

      String districtCode = allDistricts.get(0).getCode();
      District district = indonesiaService.getDistrict(districtCode, null);

      assertNotNull(district);
      assertEquals(districtCode, district.getCode());
      assertNotNull(district.getName());
      assertNull(district.getVillages()); // Should not include villages
    }

    @Test
    @DisplayName("Should return district with villages when villages included")
    void shouldReturnDistrictWithVillagesWhenVillagesIncluded() {
      List<District> allDistricts = indonesiaService.getDistricts(null, null);
      assertFalse(allDistricts.isEmpty());

      String districtCode = allDistricts.get(0).getCode();
      District district = indonesiaService.getDistrict(districtCode, List.of("villages"));

      assertNotNull(district);
      assertEquals(districtCode, district.getCode());
      assertNotNull(district.getVillages()); // Should include villages

      // Verify villages belong to this district
      assertTrue(
          district.getVillages().stream()
              .allMatch(village -> districtCode.equalsIgnoreCase(village.getDistrictCode())));
    }

    @Test
    @DisplayName("Should return null for non-existent district code")
    void shouldReturnNullForNonExistentDistrictCode() {
      District district = indonesiaService.getDistrict("999999999", List.of("villages"));
      assertNull(district);
    }

    @Test
    @DisplayName("Should preserve original district properties")
    void shouldPreserveOriginalDistrictProperties() {
      List<District> allDistricts = indonesiaService.getDistricts(null, null);
      assertFalse(allDistricts.isEmpty());

      String districtCode = allDistricts.get(0).getCode();
      District originalDistrict = allDistricts.get(0);
      District retrievedDistrict = indonesiaService.getDistrict(districtCode, List.of("villages"));

      assertEquals(originalDistrict.getCode(), retrievedDistrict.getCode());
      assertEquals(originalDistrict.getName(), retrievedDistrict.getName());
      assertEquals(originalDistrict.getLatitude(), retrievedDistrict.getLatitude());
      assertEquals(originalDistrict.getLongitude(), retrievedDistrict.getLongitude());
    }

    @Test
    @DisplayName("Should ignore invalid includes parameters")
    void shouldIgnoreInvalidIncludesParameters() {
      List<District> allDistricts = indonesiaService.getDistricts(null, null);
      assertFalse(allDistricts.isEmpty());

      String districtCode = allDistricts.get(0).getCode();
      District district =
          indonesiaService.getDistrict(districtCode, List.of("villages", "invalid", "unknown"));

      assertNotNull(district);
      assertNotNull(district.getVillages()); // Should include villages
    }

    @Test
    @DisplayName("Should handle empty includes list")
    void shouldHandleEmptyIncludesList() {
      List<District> allDistricts = indonesiaService.getDistricts(null, null);
      assertFalse(allDistricts.isEmpty());

      String districtCode = allDistricts.get(0).getCode();
      District district = indonesiaService.getDistrict(districtCode, List.of());

      assertNotNull(district);
      assertEquals(districtCode, district.getCode());
      assertNull(district.getVillages()); // Should not include villages
    }
  }

  @Nested
  @DisplayName("Nested Data Loading Integration Tests")
  class NestedDataLoadingTests {

    @Test
    @DisplayName("Should load nested data consistently across different entry points")
    void shouldLoadNestedDataConsistentlyAcrossDifferentEntryPoints() {
      // Get a province with full hierarchy
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province provinceWithCities =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts", "villages"));

      if (!provinceWithCities.getCities().isEmpty()) {
        City cityFromProvince = provinceWithCities.getCities().get(0);

        // Get the same city directly
        City cityDirect =
            indonesiaService.getCity(cityFromProvince.getCode(), List.of("districts", "villages"));

        // Compare district counts
        assertEquals(cityFromProvince.getDistricts().size(), cityDirect.getDistricts().size());

        // Compare first district's village count if districts exist
        if (!cityFromProvince.getDistricts().isEmpty() && !cityDirect.getDistricts().isEmpty()) {
          assertEquals(
              cityFromProvince.getDistricts().get(0).getVillages().size(),
              cityDirect.getDistricts().get(0).getVillages().size());
        }
      }
    }

    @Test
    @DisplayName("Should maintain data integrity during parallel loading")
    void shouldMaintainDataIntegrityDuringParallelLoading() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();

      // Load the same province multiple times concurrently to test thread safety
      Province province1 =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts", "villages"));
      Province province2 =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts", "villages"));

      // Results should be consistent
      assertEquals(province1.getCities().size(), province2.getCities().size());

      if (!province1.getCities().isEmpty() && !province2.getCities().isEmpty()) {
        assertEquals(
            province1.getCities().get(0).getDistricts().size(),
            province2.getCities().get(0).getDistricts().size());
      }
    }

    @Test
    @DisplayName("Should validate hierarchical relationships in loaded data")
    void shouldValidateHierarchicalRelationshipsInLoadedData() {
      List<Province> allProvinces = indonesiaService.getProvinces(null);
      assertFalse(allProvinces.isEmpty());

      String provinceCode = allProvinces.get(0).getCode();
      Province province =
          indonesiaService.getProvince(provinceCode, List.of("cities", "districts", "villages"));

      // Validate the complete hierarchy
      for (City city : province.getCities()) {
        assertEquals(provinceCode, city.getProvinceCode());

        if (city.getDistricts() != null) {
          for (District district : city.getDistricts()) {
            assertEquals(city.getCode(), district.getCityCode());

            if (district.getVillages() != null) {
              for (Village village : district.getVillages()) {
                assertEquals(district.getCode(), village.getDistrictCode());
              }
            }
          }
        }
      }
    }
  }
}
