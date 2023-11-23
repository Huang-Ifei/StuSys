package service;

import sever.StudentList;
//import org.json.JSONObject;
import com.alibaba.fastjson2.JSONObject;

import java.io.*;

public class ReadOut {
    public ReadOut() {

    }
    public StudentList readOutAClass(String className) {
        StudentList studentList = new StudentList();
        studentList.createdClass();
        String filePath = "database\\" + className ;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject jb = JSONObject.parseObject(line);
                Long stuNum=0L;
                String name="";
                Double point=0.0;
                stuNum = jb.getLong("stuNum");
                name = jb.getString("name");
                point = jb.getDouble("point");
                studentList.addStu(stuNum,name,point);
            }
        } catch (FileNotFoundException e) {
            studentList.addStu(0,"未找到class",0);
        } catch (IOException e) {
            studentList.addStu(0,"IO错误",0);
        }
        return studentList;
    }
    public String readOutClass(){
        File file = new File("database");
        File[] files = file.listFiles();
        String classes = "";
        for (int i=0;i<files.length;i++){
            if (!files[i].getName().equals("base")){
                System.out.println(files[i].getName());
                classes+=(files[i].getName()+"\n");
            }
        }
        return classes;
    }
}