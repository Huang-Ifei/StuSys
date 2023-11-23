package sever.dao;

import sever.Student;
import sever.StudentList;
import sql.DbOperation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentDAO {
    public String errorMessage = "";
    public void insert(Student s){
        String sql;
        sql="insert into student(stuNum,name,point)values(";
        sql+=s.getStuNum();
        sql+=",'";
        sql+=s.getName();
        sql+="',";
        sql+=s.getPoint();
        sql+=")";
        DbOperation db = new DbOperation();
        try {
            Connection connection =db.getConnection();
            db.update(connection,sql);
            db.close(connection);
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        }
    }
    public StudentList getStudentList(){
        List<Student> list =null;
        StudentList studentClass = new StudentList();
        String sql ="select stuNum,name,point from student";
        DbOperation dbOperation = new DbOperation();
        try{
            Connection connection = dbOperation.getConnection();
            list = dbOperation.getAll(connection,sql);
            dbOperation.close(connection);
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        }
        studentClass.createdClass();
        if (list != null) {
            for(int i=0;i<list.size();i++){
              studentClass.addStu(list.get(i).getStuNum(),list.get(i).getName(),list.get(i).getPoint());
            }
        }
        return studentClass;
    }
    public Student getStudent(long stuNum){
        Student student = new Student(0,"",0.0);
        String sql ="select *from student where stuNum like ";
        sql+=stuNum;
        sql+=";";
        DbOperation dbOperation = new DbOperation();
        try {
            Connection connection = dbOperation.getConnection();
            student = dbOperation.getStudentByNum(connection,sql);
            dbOperation.close(connection);
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        }
        return student;
    }
    public void deleteByNum (long stuNum){
        String sql ="delete from student where stuNum = ";
        sql+=stuNum;
        sql+=";";
        DbOperation dbOperation = new DbOperation();
        try {
            Connection connection = dbOperation.getConnection();
            dbOperation.deleteByNum(connection,sql);
            dbOperation.close(connection);
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        }
    }
    public void updateByNum (long stuNum,long newStuNum,String newName,double newPoint){
        String sql = "update student set name = '";
        sql+=newName;
        sql+="' where stuNum = ";
        sql+=stuNum;
        sql+=";";
        DbOperation dbOperation = new DbOperation();
        try {
            Connection connection = dbOperation.getConnection();
            dbOperation.updateByNum(connection,sql);
            dbOperation.close(connection);
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        }
        sql= "update student set point = ";
        sql+=newPoint;
        sql+=" where stuNum = ";
        sql+=stuNum;
        sql+=";";
        try {
            Connection connection = dbOperation.getConnection();
            dbOperation.updateByNum(connection,sql);
            dbOperation.close(connection);
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        }
        sql= "update student set stuNum = ";
        sql+=newStuNum;
        sql+=" where stuNum = ";
        sql+=stuNum;
        sql+=";";
        try {
            Connection connection = dbOperation.getConnection();
            dbOperation.updateByNum(connection,sql);
            dbOperation.close(connection);
        } catch (ClassNotFoundException e) {
            errorMessage =1002+"数据库驱动程序不存在";
        } catch (SQLException e) {
            errorMessage =1001+"数据库操作错误";
        }
    }
}