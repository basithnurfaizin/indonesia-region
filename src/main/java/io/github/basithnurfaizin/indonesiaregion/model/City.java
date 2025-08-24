package io.github.basithnurfaizin.indonesiaregion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class City {

  private String code;
  private String provinceCode;
  private String name;
  private Double latitude;
  private Double longitude;
  private List<District> districts;
}
