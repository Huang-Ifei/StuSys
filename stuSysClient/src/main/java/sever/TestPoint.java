package sever;

import java.io.Serializable;

public class TestPoint implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private double point;
    private String test;

    public TestPoint(long id, double point) {
        this.id = id;
        this.point = point;
    }
    public TestPoint(String test,long id, double point) {
        this.test=test;
        this.id = id;
        this.point = point;
    }
    public TestPoint() {
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public long getId() {
        return id;
    }

    public double getPoint() {
        return point;
    }

    public String getTest() {
        return test;
    }
}
