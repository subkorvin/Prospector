package ru.qa.rtsoft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("user")
@Entity
@Table (name = "addressbook")
public class UserData {
  @XStreamOmitField
  @Id
  @Column (name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column (name = "firstname")
  private String first_name;

  @Expose
  @Column (name = "middlename")
  private String middle_name;

  @Expose
  @Column (name = "lastname")
  private String family_name;

  @Expose
  @Column (name = "nickname")
  private String nickname;
  private String company;

  @Type(type = "text")
  private String address;

  @Column (name = "home")
  @Type(type = "text")
  private String home_phone;

  @Column (name = "mobile")
  @Type(type = "text")
  private String cell_phone;

  @Column (name = "work")
  @Type(type = "text")
  private String work_phone;

  @Type(type = "text")
  private String email;

  @Type(type = "text")
  private String email2;

  @Type(type = "text")
  private String email3;

//  @Expose
//  @Transient
//  private String group;

  @ManyToMany (fetch = FetchType.EAGER)
  @JoinTable (name = "address_in_groups", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  @Transient
  private String allPhones;

  @Transient
  private String allEmails;

  @Expose
  @Column (name = "photo")
  @Type(type = "text")
  private String photo;


  //Getters
  public String getFirst_name() {
    return first_name;
  }

  public String getMiddle_name() {
    return middle_name;
  }

  public String getFamily_name() {
    return family_name;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHome_phone() {
    return home_phone;
  }

  public String getCell_phone() {
    return cell_phone;
  }

  public String getWork_phone() {
    return work_phone;
  }

//  public String getGroup() {
//    return group;
//  }


  public Groups getGroups() {
    return new Groups(groups);
  }

  public String getAllPhones() {
    return allPhones;
  }

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public File getPhoto() {
    return new File(photo);
  }

  //Setters
  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public UserData withFirst_name(String first_name) {
    this.first_name = first_name;
    return this;
  }

  public UserData withMiddle_name(String middle_name) {
    this.middle_name = middle_name;
    return this;
  }

  public UserData withFamily_name(String family_name) {
    this.family_name = family_name;
    return this;
  }

  public UserData withNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public UserData withCompany(String company) {
    this.company = company;
    return this;
  }

  public UserData withAddress(String address) {
    this.address = address;
    return this;
  }

  public UserData withHome_phone(String home_phone) {
    this.home_phone = home_phone;
    return this;
  }

  public UserData withCell_phone(String cell_phone) {
    this.cell_phone = cell_phone;
    return this;
  }

  public UserData withWork_phone(String work_phone) {
    this.work_phone = work_phone;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

//  public UserData withGroup(String group) {
//    this.group = group;
//    return this;
//  }

  public UserData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public UserData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public UserData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public UserData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public UserData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }


  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", first_name='" + first_name + '\'' +
            ", middle_name='" + middle_name + '\'' +
            ", family_name='" + family_name + '\'' +
            ", nickname='" + nickname + '\'' +
            ", company='" + company + '\'' +
            ", address='" + address + '\'' +
            ", home_phone='" + home_phone + '\'' +
            ", cell_phone='" + cell_phone + '\'' +
            ", work_phone='" + work_phone + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", allPhones='" + allPhones + '\'' +
            ", allEmails='" + allEmails + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserData userData = (UserData) o;

    if (id != userData.id) return false;
    if (first_name != null ? !first_name.equals(userData.first_name) : userData.first_name != null) return false;
    if (middle_name != null ? !middle_name.equals(userData.middle_name) : userData.middle_name != null) return false;
    if (family_name != null ? !family_name.equals(userData.family_name) : userData.family_name != null) return false;
    if (nickname != null ? !nickname.equals(userData.nickname) : userData.nickname != null) return false;
    if (company != null ? !company.equals(userData.company) : userData.company != null) return false;
    if (address != null ? !address.equals(userData.address) : userData.address != null) return false;
    if (home_phone != null ? !home_phone.equals(userData.home_phone) : userData.home_phone != null) return false;
    if (cell_phone != null ? !cell_phone.equals(userData.cell_phone) : userData.cell_phone != null) return false;
    if (work_phone != null ? !work_phone.equals(userData.work_phone) : userData.work_phone != null) return false;
    if (email != null ? !email.equals(userData.email) : userData.email != null) return false;
    if (email2 != null ? !email2.equals(userData.email2) : userData.email2 != null) return false;
    return email3 != null ? email3.equals(userData.email3) : userData.email3 == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
    result = 31 * result + (middle_name != null ? middle_name.hashCode() : 0);
    result = 31 * result + (family_name != null ? family_name.hashCode() : 0);
    result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
    result = 31 * result + (company != null ? company.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (home_phone != null ? home_phone.hashCode() : 0);
    result = 31 * result + (cell_phone != null ? cell_phone.hashCode() : 0);
    result = 31 * result + (work_phone != null ? work_phone.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (email2 != null ? email2.hashCode() : 0);
    result = 31 * result + (email3 != null ? email3.hashCode() : 0);
    return result;
  }

  public UserData inGroup(GroupData selectedGroup) {
    groups.add(selectedGroup);
    return this;
  }
}
