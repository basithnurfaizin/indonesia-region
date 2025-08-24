# Indonesia Region

A simple Java library to provide **Indonesia administrative regions** data (Provinces, Cities/Regencies, Districts, and Villages).  
This project is inspired by the excellent [laravolt/indonesia](https://github.com/laravolt/indonesia) package for Laravel/PHP.

## Features

- ✅ List all provinces, cities, districts, and villages of Indonesia.
- ✅ Search by code or name (with keyword filter).
- ✅ Filter by parent region (e.g., cities by province, districts by city).
- ✅ Data stored locally for fast access (no external API calls).

## Installation

Add the dependency to your Maven `pom.xml`:

```xml
<dependency>
  <groupId>io.github.basithnurfaizin</groupId>
  <artifactId>indonesiaregion</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage
```java
IndonesiaService indonesiaService = new IndonesiaServiceImpl();

// Get all provinces
List<Province> provinces = indonesiaService.getProvinces(null);

// Get cities in a province
List<City> cities = indonesiaService.getCities("32", null);

// Search districts by keyword
List<District> districts = indonesiaService.getDistricts("3273", "Cimahi");

// Get villages in a district
List<Village> villages = indonesiaService.getVillages("3273010", null);

// Get a single province with cities, districts, and villages
Province westJava =
        indonesiaService.getProvince("32", List.of("cities", "districts", "villages"));

// Get a single city with districts and villages
City cimahi =
        indonesiaService.getCity("3273", List.of("districts", "villages"));

// Get a single district with villages
District cimahiUtara =
        indonesiaService.getDistrict("3273010", List.of("villages"));

```

## Includes Parameter

When fetching a single region (`getProvince`, `getCity`, `getDistrict`),  
you can pass a list of strings (`includes`) to specify which nested data should be loaded.

- `"cities"` → load cities for a province
- `"districts"` → load districts for a city (or for each city if called on a province)
- `"villages"` → load villages for a district (or for each district if higher level is requested)

### Example

```java
Province province = indonesiaService.getProvince("32", List.of("cities", "districts", "villages"));
```

## Roadmap

- [ ] Add Gradle support
- [ ] Publish to Maven Central
- [ ] Provide REST API wrapper (Spring Boot starter)

## Credits

- Inspired by [laravolt/indonesia](https://github.com/laravolt/indonesia)
- Data source: Kemendagri (Indonesia Ministry of Home Affairs)

## License

MIT License. See [LICENSE](LICENSE) for details.
