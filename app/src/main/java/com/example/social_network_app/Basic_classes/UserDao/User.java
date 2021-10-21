package com.example.social_network_app.Basic_classes.UserDao;


import androidx.annotation.NonNull;

import java.io.Serializable;


/**
 * @author Yuhui Pang
 *
 * The user class
 */
public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private int age;
    private String sex;
    private String head;
    private int fans;

    public User(int id, String name, String email, int age, String sex,String head,int fans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.head = head;
        this.fans = fans;
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

    public String getHead() {
        return head;
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

    public void setHeed(String head) {
        this.head = head;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", heed='" + head + '\'' +
                '}';
    }

    public boolean equals(User user){
        return this.id == user.id;
    }
}
