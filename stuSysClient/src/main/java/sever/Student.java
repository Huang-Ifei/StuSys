package sever;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private long stuNum;
    private String name;
    private double point;
    public Student(long studentNum, String name, double point) {
        this.stuNum = studentNum;
        this.name=name;
        this.point=point;
    }
    public double getPoint(){
        return point;
    }
    public String getName() {
        return name;
    }
    public long getStuNum() {
        return stuNum;
    }
}