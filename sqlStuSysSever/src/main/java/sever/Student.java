package sever;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String sex;
    private String address;
    public Student(long id, String name, String sex, String address) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.address = address;
    }
    public Student() {
    }
    public Student(String name){
        this.id = 0L;
        this.name = name;
        this.sex = "sex";
        this.address = "address";
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

}