package io.github.basithnurfaizin.indonesiaregion.model;

public class District {

  private String code;

  private String name;

  private String cityCode;

  private double latitude;

  private double longitude;

  public District(String code, String name, String cityCode, double latitude, double longitude) {
    this.code = code;
    this.name = name;
    this.cityCode = cityCode;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return super.toString();
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

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}
