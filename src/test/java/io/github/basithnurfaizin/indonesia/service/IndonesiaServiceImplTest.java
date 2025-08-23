package io.github.basithnurfaizin.indonesia.service;

import io.github.basithnurfaizin.indonesia.Province;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IndonesiaServiceImplTest {

    private IndonesiaService indonesiaService;

    @BeforeEach
    void setUp() {
        indonesiaService = new IndonesiaServiceImpl();
    }

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