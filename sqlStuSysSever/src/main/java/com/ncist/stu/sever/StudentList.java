package com.ncist.stu.sever;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Student> stuList;
    private int size;

    public StudentList() {
        size = 0;
        stuList = null;
    }
    public void createdClass() {
        stuList = new ArrayList<Student>();
    }
    public void addStu(long stuNum, String name, String sex,String address) {
        stuList.add(new Student(stuNum, name, sex,address));
        size++;
    }
    public List<Student> getStuList() {
        return stuList;
    }
    public List<Student> numSortOutput() {
        Student temp;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 1; j < size - i; j++) {
                if (stuList.get(j - 1).getId() > stuList.get(j).getId()) {
                    temp = stuList.get(j - 1);
                    stuList.set(j - 1, stuList.get(j));
                    stuList.set(j, temp);
                }
            }
        }
        return stuList;
    }
}
