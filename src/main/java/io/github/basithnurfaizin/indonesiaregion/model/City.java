package io.github.basithnurfaizin.indonesiaregion.model;

public class City {

  private String code;
  private String provinceCode;
  private String name;
  private Double latitude;
  private Double longitude;

  public City(String code, String provinceCode, String name, Double latitude, Double longitude) {
    this.code = code;
    this.provinceCode = provinceCode;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
}
