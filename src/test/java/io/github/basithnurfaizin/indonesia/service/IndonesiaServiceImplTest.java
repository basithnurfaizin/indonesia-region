package io.github.basithnurfaizin.indonesia.service;

import io.github.basithnurfaizin.indonesia.model.City;
import io.github.basithnurfaizin.indonesia.model.Province;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IndonesiaServiceImplTest {

    private IndonesiaService indonesiaService;

    @BeforeEach
    void setUp() {
        indonesiaService = new IndonesiaServiceImpl();
    }

    @Nested
    @DisplayName("Province Test")
    class ProvinceTest {
        @Test
        @DisplayName("Should return all provinces when keyword is null")
        void shouldReturnAllProvincesWhenKeywordIsNull() {
            // When
            List<Province> provinces = indonesiaService.getProvinces(null);

            // Then
            assertNotNull(provinces);
            assertFalse(provinces.isEmpty());

            // Verify provinces are sorted by code
            for (int i = 1; i < provinces.size(); i++) {
                String prevCode = provinces.get(i - 1).getCode();
                String currentCode = provinces.get(i).getCode();
                assertTrue(prevCode.compareTo(currentCode) <= 0,
                        "Provinces should be sorted by code");
            }
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Should return all provinces when keyword is null, empty, or blank")
        void shouldReturnAllProvincesWhenKeywordIsNullEmptyOrBlank(String keyword) {
            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);
            assertFalse(provinces.isEmpty());
        }

        @Test
        @DisplayName("Should filter provinces by exact code match (case insensitive)")
        void shouldFilterProvincesByExactCodeMatch() {
            // Given - assuming there's a province with code "JK" (Jakarta)
            String keyword = "jk"; // lowercase to test case insensitivity

            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);

            // Should contain provinces that match the code exactly (case insensitive)
            provinces.forEach(province -> {
                boolean codeMatch = province.getCode().equalsIgnoreCase(keyword);
                boolean nameMatch = province.getName().toLowerCase().contains(keyword.toLowerCase());
                assertTrue(codeMatch || nameMatch,
                        "Province should match either by code or name");
            });
        }

        @Test
        @DisplayName("Should filter provinces by partial name match (case insensitive)")
        void shouldFilterProvincesByPartialNameMatch() {
            // Given - assuming there are provinces with "java" in the name
            String keyword = "java";

            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);

            // All returned provinces should contain "java" in their name (case insensitive)
            provinces.forEach(province -> {
                boolean codeMatch = province.getCode().equalsIgnoreCase(keyword);
                boolean nameMatch = province.getName().toLowerCase().contains(keyword.toLowerCase());
                assertTrue(codeMatch || nameMatch,
                        "Province should match either by code or name containing: " + keyword);
            });
        }

        @Test
        @DisplayName("Should filter provinces by partial name match with different cases")
        void shouldFilterProvincesByPartialNameMatchWithDifferentCases() {
            // Given
            String[] keywords = {"JAVA", "Java", "jAvA", "java"};

            // When & Then
            List<Province> expectedProvinces = indonesiaService.getProvinces("java");

            for (String keyword : keywords) {
                List<Province> actualProvinces = indonesiaService.getProvinces(keyword);
                assertEquals(expectedProvinces.size(), actualProvinces.size(),
                        "Should return same results regardless of case for keyword: " + keyword);
            }
        }

        @Test
        @DisplayName("Should return empty list when no provinces match the keyword")
        void shouldReturnEmptyListWhenNoProvincesMatchKeyword() {
            // Given - a keyword that likely won't match any province
            String keyword = "nonexistentprovince123";

            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);
            assertTrue(provinces.isEmpty(), "Should return empty list when no matches found");
        }

        @Test
        @DisplayName("Should handle special characters in keyword")
        void shouldHandleSpecialCharactersInKeyword() {
            // Given
            String keyword = "D.I."; // Special characters that might be in province names

            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);
            // The test should not throw any exception
        }

        @Test
        @DisplayName("Should maintain sorted order by code in filtered results")
        void shouldMaintainSortedOrderByCodeInFilteredResults() {
            // Given - a keyword that should match multiple provinces
            String keyword = "a"; // Should match many provinces containing 'a'

            // When
            List<Province> provinces = indonesiaService.getProvinces(keyword);

            // Then
            assertNotNull(provinces);

            if (provinces.size() > 1) {
                // Verify the results are sorted by code
                for (int i = 1; i < provinces.size(); i++) {
                    String prevCode = provinces.get(i - 1).getCode();
                    String currentCode = provinces.get(i).getCode();
                    assertTrue(prevCode.compareTo(currentCode) <= 0,
                            "Filtered provinces should be sorted by code");
                }
            }
        }

        @Test
        @DisplayName("Should handle Unicode characters in keyword")
        void shouldHandleUnicodeCharactersInKeyword() {
            // Given
            String keyword = "yogyakarta"; // or any keyword with potential Unicode

            // When & Then
            assertDoesNotThrow(() -> {
                List<Province> provinces = indonesiaService.getProvinces(keyword);
                assertNotNull(provinces);
            }, "Should handle Unicode characters without throwing exception");
        }

        @Test
        @DisplayName("Should return same instance type for Province objects")
        void shouldReturnSameInstanceTypeForProvinceObjects() {
            // When
            List<Province> provinces = indonesiaService.getProvinces(null);

            // Then
            assertNotNull(provinces);
            if (!provinces.isEmpty()) {
                Province province = provinces.get(0);
                assertNotNull(province.getCode(), "Province code should not be null");
                assertNotNull(province.getName(), "Province name should not be null");
                assertFalse(province.getCode().isBlank(), "Province code should not be blank");
                assertFalse(province.getName().isBlank(), "Province name should not be blank");
            }
        }

        @Test
        @DisplayName("Should be case insensitive for both code and name matching")
        void shouldBeCaseInsensitiveForBothCodeAndNameMatching() {
            // Given
            List<Province> allProvinces = indonesiaService.getProvinces(null);

            if (!allProvinces.isEmpty()) {
                Province firstProvince = allProvinces.get(0);
                String originalCode = firstProvince.getCode();
                String originalName = firstProvince.getName();

                // Test code matching with different cases
                List<Province> upperCaseCodeResult = indonesiaService.getProvinces(originalCode.toUpperCase());
                List<Province> lowerCaseCodeResult = indonesiaService.getProvinces(originalCode.toLowerCase());

                // Should find the province regardless of case
                boolean foundInUpperCase = upperCaseCodeResult.stream()
                        .anyMatch(p -> p.getCode().equals(originalCode));
                boolean foundInLowerCase = lowerCaseCodeResult.stream()
                        .anyMatch(p -> p.getCode().equals(originalCode));

                assertTrue(foundInUpperCase, "Should find province with uppercase code");
                assertTrue(foundInLowerCase, "Should find province with lowercase code");

                // Test partial name matching with different cases
                if (originalName.length() > 2) {
                    String partialName = originalName.substring(0, 3);
                    List<Province> upperCaseNameResult = indonesiaService.getProvinces(partialName.toUpperCase());
                    List<Province> lowerCaseNameResult = indonesiaService.getProvinces(partialName.toLowerCase());

                    boolean foundInUpperCaseName = upperCaseNameResult.stream()
                            .anyMatch(p -> p.getName().equals(originalName));
                    boolean foundInLowerCaseName = lowerCaseNameResult.stream()
                            .anyMatch(p -> p.getName().equals(originalName));

                    assertTrue(foundInUpperCaseName, "Should find province with uppercase partial name");
                    assertTrue(foundInLowerCaseName, "Should find province with lowercase partial name");
                }
            }
        }
    }

    @Nested
    @DisplayName("City Test")
    class CityTest {

        @Test
        @DisplayName("Should return all cities when both provinceCode and keyword are null")
        void shouldReturnAllCitiesWhenBothParametersAreNull() {
            // When
            List<City> cities = indonesiaService.getCities(null, null);

            // Then
            assertNotNull(cities);
            assertFalse(cities.isEmpty());

            // Verify cities are sorted by code
            for (int i = 1; i < cities.size(); i++) {
                String prevCode = cities.get(i - 1).getCode();
                String currentCode = cities.get(i).getCode();
                assertTrue(prevCode.compareTo(currentCode) <= 0,
                        "Cities should be sorted by code");
            }
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Should return all cities when provinceCode is null/empty/blank and keyword is null")
        void shouldReturnAllCitiesWhenProvinceCodeIsNullEmptyOrBlankAndKeywordIsNull(String provinceCode) {
            // When
            List<City> cities = indonesiaService.getCities(provinceCode, null);

            // Then
            assertNotNull(cities);
            assertFalse(cities.isEmpty());
        }

        @Test
        @DisplayName("Should filter cities by provinceCode only (case insensitive)")
        void shouldFilterCitiesByProvinceCodeOnly() {
            // Given - get all cities to find a valid province code
            List<City> allCities = indonesiaService.getCities(null, null);
            assertFalse(allCities.isEmpty(), "Should have cities for testing");

            String testProvinceCode = allCities.get(0).getProvinceCode();

            // When
            List<City> cities = indonesiaService.getCities(testProvinceCode, null);

            // Then
            assertNotNull(cities);
            assertFalse(cities.isEmpty());

            // All returned cities should belong to the specified province
            cities.forEach(city ->
                    assertEquals(testProvinceCode.toUpperCase(), city.getProvinceCode().toUpperCase(),
                            "All cities should belong to province: " + testProvinceCode));
        }

        @Test
        @DisplayName("Should filter cities by provinceCode case insensitively")
        void shouldFilterCitiesByProvinceCodeCaseInsensitively() {
            // Given
            List<City> allCities = indonesiaService.getCities(null, null);
            assertFalse(allCities.isEmpty(), "Should have cities for testing");

            String originalProvinceCode = allCities.get(0).getProvinceCode();
            String[] provinceCodes = {
                    originalProvinceCode.toUpperCase(),
                    originalProvinceCode.toLowerCase(),
                    capitalizeFirst(originalProvinceCode)
            };

            List<City> expectedCities = indonesiaService.getCities(originalProvinceCode, null);

            // When & Then
            for (String provinceCode : provinceCodes) {
                List<City> cities = indonesiaService.getCities(provinceCode, null);
                assertEquals(expectedCities.size(), cities.size(),
                        "Should return same results regardless of case for province code: " + provinceCode);
            }
        }

        @Test
        @DisplayName("Should filter cities by keyword only (search in both code and name)")
        void shouldFilterCitiesByKeywordOnly() {
            // Given
            String keyword = "a"; // Should match many cities

            // When
            List<City> cities = indonesiaService.getCities(null, keyword);

            // Then
            assertNotNull(cities);

            // All returned cities should match the keyword in either code or name
            cities.forEach(city -> {
                boolean codeMatch = city.getCode().toLowerCase().contains(keyword.toLowerCase());
                boolean nameMatch = city.getName().toLowerCase().contains(keyword.toLowerCase());
                assertTrue(codeMatch || nameMatch,
                        "City should match keyword in either code or name: " + city.getName());
            });
        }

        @Test
        @DisplayName("Should filter cities by both provinceCode and keyword")
        void shouldFilterCitiesByBothProvinceCodeAndKeyword() {
            // Given
            List<City> allCities = indonesiaService.getCities(null, null);
            assertFalse(allCities.isEmpty(), "Should have cities for testing");

            String testProvinceCode = allCities.get(0).getProvinceCode();
            String keyword = "a"; // Should match some cities

            // When
            List<City> cities = indonesiaService.getCities(testProvinceCode, keyword);

            // Then
            assertNotNull(cities);

            cities.forEach(city -> {
                // Should match province code
                assertEquals(testProvinceCode.toUpperCase(), city.getProvinceCode().toUpperCase(),
                        "City should belong to province: " + testProvinceCode);

                // Should match keyword in either code or name
                boolean codeMatch = city.getCode().toLowerCase().contains(keyword.toLowerCase());
                boolean nameMatch = city.getName().toLowerCase().contains(keyword.toLowerCase());
                assertTrue(codeMatch || nameMatch,
                        "City should match keyword in either code or name");
            });
        }

        @ParameterizedTest
        @MethodSource("provideCaseVariations")
        @DisplayName("Should be case insensitive for keyword matching")
        void shouldBeCaseInsensitiveForKeywordMatching(String keyword) {
            // When
            List<City> cities = indonesiaService.getCities(null, keyword);

            // Then
            assertNotNull(cities);

            List<City> expectedCities = indonesiaService.getCities(null, keyword.toLowerCase());
            assertEquals(expectedCities.size(), cities.size(),
                    "Should return same results regardless of keyword case");
        }

        static Stream<Arguments> provideCaseVariations() {
            return Stream.of(
                    Arguments.of("Jakarta"),
                    Arguments.of("JAKARTA"),
                    Arguments.of("jakarta"),
                    Arguments.of("JaKaRtA")
            );
        }

        @Test
        @DisplayName("Should return empty list when no cities match the criteria")
        void shouldReturnEmptyListWhenNoCitiesMatchCriteria() {
            // Given
            String nonExistentProvinceCode = "NONEXISTENT";
            String nonExistentKeyword = "nonexistentcity123";

            // When & Then
            List<City> citiesByProvince = indonesiaService.getCities(nonExistentProvinceCode, null);
            assertNotNull(citiesByProvince);
            assertTrue(citiesByProvince.isEmpty(), "Should return empty list for non-existent province");

            List<City> citiesByKeyword = indonesiaService.getCities(null, nonExistentKeyword);
            assertNotNull(citiesByKeyword);
            assertTrue(citiesByKeyword.isEmpty(), "Should return empty list for non-existent keyword");

            List<City> citiesByBoth = indonesiaService.getCities(nonExistentProvinceCode, nonExistentKeyword);
            assertNotNull(citiesByBoth);
            assertTrue(citiesByBoth.isEmpty(), "Should return empty list for both non-existent criteria");
        }

        @Test
        @DisplayName("Should maintain sorted order by code in all scenarios")
        void shouldMaintainSortedOrderByCodeInAllScenarios() {
            // Test sorting with no filters
            List<City> allCities = indonesiaService.getCities(null, null);
            verifyCitiesSortedByCode(allCities);

            // Test sorting with province filter only
            if (!allCities.isEmpty()) {
                String provinceCode = allCities.get(0).getProvinceCode();
                List<City> citiesByProvince = indonesiaService.getCities(provinceCode, null);
                verifyCitiesSortedByCode(citiesByProvince);
            }

            // Test sorting with keyword filter only
            List<City> citiesByKeyword = indonesiaService.getCities(null, "a");
            verifyCitiesSortedByCode(citiesByKeyword);

            // Test sorting with both filters
            if (!allCities.isEmpty()) {
                String provinceCode = allCities.get(0).getProvinceCode();
                List<City> citiesByBoth = indonesiaService.getCities(provinceCode, "a");
                verifyCitiesSortedByCode(citiesByBoth);
            }
        }

        private void verifyCitiesSortedByCode(List<City> cities) {
            if (cities.size() > 1) {
                for (int i = 1; i < cities.size(); i++) {
                    String prevCode = cities.get(i - 1).getCode();
                    String currentCode = cities.get(i).getCode();
                    assertTrue(prevCode.compareTo(currentCode) <= 0,
                            "Cities should be sorted by code");
                }
            }
        }

        @Test
        @DisplayName("Should handle special characters in keyword")
        void shouldHandleSpecialCharactersInKeyword() {
            // Given
            String keyword = "D.I."; // Special characters that might be in city names

            // When & Then
            assertDoesNotThrow(() -> {
                List<City> cities = indonesiaService.getCities(null, keyword);
                assertNotNull(cities);
            }, "Should handle special characters without throwing exception");
        }

        @Test
        @DisplayName("Should handle Unicode characters in keyword")
        void shouldHandleUnicodeCharactersInKeyword() {
            // Given
            String keyword = "yogyakarta"; // or any keyword with potential Unicode

            // When & Then
            assertDoesNotThrow(() -> {
                List<City> cities = indonesiaService.getCities(null, keyword);
                assertNotNull(cities);
            }, "Should handle Unicode characters without throwing exception");
        }

        @Test
        @DisplayName("Should return valid City objects with required fields")
        void shouldReturnValidCityObjectsWithRequiredFields() {
            // When
            List<City> cities = indonesiaService.getCities(null, null);

            // Then
            assertNotNull(cities);
            if (!cities.isEmpty()) {
                City city = cities.get(0);
                assertNotNull(city.getCode(), "City code should not be null");
                assertNotNull(city.getName(), "City name should not be null");
                assertNotNull(city.getProvinceCode(), "City province code should not be null");
                assertFalse(city.getCode().isBlank(), "City code should not be blank");
                assertFalse(city.getName().isBlank(), "City name should not be blank");
                assertFalse(city.getProvinceCode().isBlank(), "City province code should not be blank");
            }
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Should ignore blank keywords and return results based on provinceCode only")
        void shouldIgnoreBlankKeywordsAndReturnResultsBasedOnProvinceCodeOnly(String keyword) {
            // Given
            List<City> allCities = indonesiaService.getCities(null, null);
            if (!allCities.isEmpty()) {
                String testProvinceCode = allCities.get(0).getProvinceCode();

                // When
                List<City> citiesWithBlankKeyword = indonesiaService.getCities(testProvinceCode, keyword);
                List<City> citiesWithNullKeyword = indonesiaService.getCities(testProvinceCode, null);

                // Then
                assertEquals(citiesWithNullKeyword.size(), citiesWithBlankKeyword.size(),
                        "Should return same results when keyword is blank as when it's null");
            }
        }

        private String capitalizeFirst(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }


}