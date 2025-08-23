package io.github.basithnurfaizin.indonesiaregion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Village {

  private String code;

  private String name;

  private String districtCode;

  private double longitude;
  private double latitude;
}
