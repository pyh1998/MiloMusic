package com.example.social_network_app.Basic_classes.UserDao;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private int age;
    private String sex;
    private String heed;

    public User(int id, String name, String email, int age, String sex) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getHeed() {
        if(sex.equals("male")) return "male_head";
        else return  "female_head";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHeed(String heed) {
        this.heed = heed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", heed='" + heed + '\'' +
                '}';
    }
}
