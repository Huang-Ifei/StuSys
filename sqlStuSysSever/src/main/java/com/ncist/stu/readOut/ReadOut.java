package com.ncist.stu.readOut;

import com.alibaba.fastjson2.JSONObject;
import com.ncist.stu.mapper.UserMapper;
import com.ncist.stu.sever.Student;
import com.ncist.stu.sever.StudentList;
import com.ncist.stu.sever.TestPoint;
import com.ncist.stu.sever.TestPointList;
import com.ncist.stu.utlis.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class ReadOut {
    private final SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    private final UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    public ReadOut() {
    }

    public String readClasses(){
        System.out.println("&&&readClasses");
        String classList = "";
        List<Map<String,Object>> maps = mapper.getAllClasses();
        for (Map<String,Object> map:maps){
            if (!map.get("table_name").equals("base")) classList += map.get("table_name")+"\n";
        }
        return classList;
    }
    public String readTests(){
        System.out.println("&&&readTests");
        String testList = "";
        List<Map<String,Object>> maps = mapper.getAllTest();
        for (Map<String,Object> map:maps){
            if (!map.get("table_name").equals("base")) testList += map.get("table_name")+"\n";
        }
        return testList;
    }
    public StudentList readAClassStu(String className){
        System.out.println("&&&readAClassStu "+className);
        StudentList studentList = new StudentList();
        studentList.createdClass();
        List<Student> list = mapper.getAClassStu(className);
        for(Student stu :list){
            studentList.addStu(stu.getId(),stu.getName(),stu.getSex(),stu.getAddress());
        }
        return studentList;
    }
    public Student readById(String className,String content){
        System.out.print("&&&readById "+className+':');
        Student student;
        JSONObject jsonObject = JSONObject.parseObject(content);
        System.out.println(jsonObject.getLong("id"));
        try {
             student = mapper.getAStuById(className,jsonObject.getLong("id"));
            student.getName();
        } catch (NullPointerException e){
            System.out.println("&&&Can't Found Student");
            return new Student(0,"未找到学生","","");
        }
        catch (Exception e){
            System.out.println("&&&Error: "+e);
            return new Student(0,"服务器错误","","");
        }
        return student;
    }
    public TestPointList readPointById(String content){
        System.out.println("&&&readPointById");
        JSONObject jsonObject = JSONObject.parseObject(content);
        List<Map<String,Object>> maps = mapper.getAllTest();
        TestPointList testPointList = new TestPointList();
        for (Map<String,Object> map:maps){
            try {
                testPointList.add(map.get("table_name").toString(),mapper.getPoint(map.get("table_name").toString(),jsonObject.getLong("id")));
            }catch (Exception e){

            }
        }
        return testPointList;
    }
    public TestPointList getATestPoint(String className){
        System.out.println("&&&getATestPoint");
        TestPointList testPointList = new TestPointList();
        List<TestPoint> list = mapper.getATestPoint(className);
        for (TestPoint testPoint:list){
            try {
                testPointList.add(className,testPoint);
            }catch (Exception e){
                System.out.println("error");
                return testPointList;
            }
        }
        return testPointList;
    }
}
