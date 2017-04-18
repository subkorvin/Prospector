package ru.qa.rtsoft.prospector.model;

/**
 * Created by korvin on 18.04.2017.
 */
public class Gateways {

  private String gatewayID;
  private String name;


  //Getters
  public String getGatewayID() {
    return gatewayID;
  }

  public String getName() {
    return name;
  }

  //Setters
  public Gateways withName(String name) {
    this.name = name;
    return this;
  }

  public Gateways setGatewayID(String gatewayID) {
    this.gatewayID = gatewayID;
    return this;
  }
}
