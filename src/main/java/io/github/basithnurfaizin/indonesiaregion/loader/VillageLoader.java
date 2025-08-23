package io.github.basithnurfaizin.indonesiaregion.loader;

import io.github.basithnurfaizin.indonesiaregion.model.Village;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VillageLoader {

  public static Map<String, Village> loadVillages() {
    Map<String, Village> villages = new HashMap<>();

    try {
      // Load resource folder
      URL resource = VillageLoader.class.getClassLoader().getResource("data/villages");
      if (resource == null) {
        return Collections.emptyMap();
      }

      Path path = Paths.get(resource.toURI());
      if (!Files.exists(path)) {
        return Collections.emptyMap();
      }

      // Walk through CSV files
      try (Stream<Path> files = Files.walk(path)) {
        files
            .filter(Files::isRegularFile)
            .filter(f -> f.toString().endsWith(".csv"))
            .forEach(file -> loadVillageFile(file, villages));
      }

    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }

    return villages;
  }

  private static void loadVillageFile(Path file, Map<String, Village> villages) {
    try (Stream<String> lines = Files.lines(file)) {
      lines.forEach(
          line -> {
            String[] cols = line.split(",");
            if (cols.length >= 5) {
              String code = cols[0].trim();
              String districtCode = cols[1].trim();
              String name = cols[2].trim();
              double lat = parseDoubleOrDefault(cols[3]);
              double lng = parseDoubleOrDefault(cols[4]);

              villages.put(code, new Village(code, name, districtCode, lat, lng));
            }
          });
    } catch (IOException e) {
      System.err.println("❌ Error reading file: " + file);
      e.printStackTrace();
    }
  }

  private static double parseDoubleOrDefault(String value) {
    try {
      return Double.parseDouble(value.trim());
    } catch (Exception e) {
      return 0.0;
    }
  }
}
