package ru.qa.rtsoft.prospector.model;

/**
 * Created by korvin on 04.04.2017.
 */
public class MailMessage {

  public String to;
  public String text;

  public MailMessage (String to, String text){
    this.to = to;
    this.text = text;
  }
}
