package io.github.basithnurfaizin.indonesiaregion.model;

public class Village {

  private String code;

  private String name;

  private String districtCode;

  private double longitude;
  private double latitude;

  public Village(String code, String name, String districtCode, double longitude, double latitude) {
    this.code = code;
    this.name = name;
    this.districtCode = districtCode;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDistrictCode() {
    return districtCode;
  }

  public void setDistrictCode(String districtCode) {
    this.districtCode = districtCode;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
}
