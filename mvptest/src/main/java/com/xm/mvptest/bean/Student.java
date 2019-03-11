package com.xm.mvptest.bean;

import java.util.Date;

/**
 * Created by XMclassmate on 2018/4/11.
 */

public class Student {

    private String id;
    private String name;
    private int age;
    private String sex;
    private Date birthday;
    private float weight;

    public Student() {
    }

    public Student(String id, String name, int age, String sex, Date birthday, float weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String eat(String food) {
        return food;
    }
}
