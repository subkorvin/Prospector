package ru.qa.rtsoft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.qa.rtsoft.addressbook.model.UserData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korvin on 19.03.2017.
 */
public class UserDataGenerator {
  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-fo", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    UserDataGenerator generator = new UserDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<UserData> users = generateUsers(count);
    if (format.equals("csv")) {
      saveAsCsv(users, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(users, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(users, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<UserData> users, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting()
            //.excludeFieldsWithoutExposeAnnotation()
            .create();
    String json = gson.toJson(users);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<UserData> users, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(UserData.class);
    String xml = xstream.toXML(users);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<UserData> users, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    try (Writer writer = new FileWriter(file)) {
      for (UserData user : users) {
//        writer.write(String.format("%s;%s;%s\n", user.getFirst_name(), user.getMiddle_name(), user.getFirst_name(), user.getGroup()));
      }
    }
  }

  private List<UserData> generateUsers(int count) {
    List<UserData> users = new ArrayList<UserData>();
    File photo = new File("src/test/resources/11698799_crop.jpg");
    for (int i = 0; i < count; i++) {
      users.add(new UserData()
              .withFirst_name("Vasia")
              .withMiddle_name("Yu")
              .withFamily_name(String.format("Ivanov %s", i))
              .withNickname(String.format("VasyaPro %s", i))
              .withCompany("NIICHAVO")
              .withPhoto(photo)
              .withAddress("Moscow, tup. Kommunizma, 13")
              .withHome_phone("+7 495 1234567")
              .withCell_phone("+7 916 1234567")
              .withWork_phone("+7 499 1234567")
              .withEmail("vasya@pupkin.ru")
              .withEmail2("vasya_pro@mail.ru")
              .withEmail3("super.vasya@gmail.com"));
//              .withGroup("Test1"));
    }
    return users;
  }
}
