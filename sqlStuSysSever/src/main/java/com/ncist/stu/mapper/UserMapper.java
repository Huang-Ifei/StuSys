package com.ncist.stu.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.ncist.stu.sever.Student;
import com.ncist.stu.sever.TestPoint;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /*
    * MyBatis面向接口编程的两个一致
    * 1.映射文件的namespace要和全类名一致
    * 2.映射文件的Sql语句中的id要和接口中的方法一致
    * 表-实体类-mapper接口-映射文件
    * */
    //添加用户信息（一个抽象方法）
    int insertStu(@Param("className")String className,@Param("id")long id,@Param("name")String name,@Param("sex")String sex,@Param("address")String address);
    Map<String,Object> getStuById(@Param("className")String className, @Param("id")long id);
    Student getAStuById(@Param("className")String className, @Param("id")long id);
    Map<String,Object> getUserByName(@Param("className")String className, @Param("name")String name);
    int deleteColumn(@Param("className")String className,@Param("column")String column);
    @MapKey("id")
    List<Map<String, Object>> getStuList(@Param("className")String className);
    List<Student> getAClassStu(@Param("className")String className);
    List<Map<String,Object>> getAllClasses();
    void createTable (String className);
    void deleteById(@Param("className")String className,@Param("id")long id);
    void updateStu(@Param("className")String className,@Param("id")long id,@Param("name")String name,@Param("sex")String sex,@Param("address")String address);

    TestPoint getPoint(@Param("testName")String className,@Param("id")long id);
    List<Map<String,Object>> getAllTest();
    List<TestPoint> getATestPoint(@Param("testName")String testName);
    void createTest(@Param("testName")String testName);
    void insertPoint(@Param("testName")String testName,@Param("id")long id,@Param("point")double point);
    void changePoint(@Param("testName")String testName,@Param("id")long id,@Param("point")double point);
    void deletePoint(@Param("testName")String tableName,@Param("id")long id);
    void deleteTest(String testName);
}
