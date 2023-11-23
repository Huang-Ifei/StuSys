package sql;

import sever.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOperation {
    public DbOperation(){}
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String sDBDriver = "com.mysql.cj.jdbc.Driver";
        String conStr = "jdbc:mysql://localhost:3306/javadb";
        String username = "root";
        String password = "root";
        Class.forName(sDBDriver);
        return DriverManager.getConnection(conStr,username,password);
    }
    public void update(Connection connection,String sql)throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }
    public void close(Connection connection)throws SQLException{
        connection.close();
    }
    public List<Student> getAll(Connection connection, String sql){
        List<Student> stuList= new ArrayList<Student>();
        Student temp;
        long stuNum;
        String name;
        double point;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                stuNum=rs.getLong("stuNum");
                name = rs.getString("name");
                point = rs.getDouble("point");
                temp = new Student(stuNum,name,point);
                stuList.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return stuList;
    }
    public Student getStudentByNum(Connection connection,String sql){
        Student student = new Student(0,"未找到学生",0.0);
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            rs.next();
            student=new Student(rs.getLong("stuNum"),rs.getString("name"),rs.getDouble("point"));
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(sql);
        return student;
    }
    public void deleteByNum(Connection connection,String sql){
        try{
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateByNum(Connection connection,String sql){
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
