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
  <artifactId>indonesia-region</artifactId>
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
