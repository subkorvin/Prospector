package ru.qa.rtsoft.prospector.model;

/**
 * Created by korvin on 07.04.2017.
 */
public class Project {

  private int id;
  private String name;

  //Getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }


  //Setters
  public Project withName(String name) {
    this.name = name;
    return this;
  }

  public Project withId(int id) {
    this.id = id;
    return this;
  }
}
