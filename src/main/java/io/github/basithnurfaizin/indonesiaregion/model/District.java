package io.github.basithnurfaizin.indonesiaregion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class District {

  private String code;

  private String name;

  private String cityCode;

  private double latitude;

  private double longitude;

  private List<Village> villages;
}
