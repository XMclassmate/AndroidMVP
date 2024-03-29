package com.xm.mvptest.bean;

import java.util.List;

/**
 * Created by XMclassmate on 2018/4/11.
 */

public class School {

    private String id;
    private String name;
    private String address;
    private String city;
    private List<Student> studentList;

    public School() {
    }

    public School(String id, String name, String address, String city, List<Student> studentList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.studentList = studentList;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
