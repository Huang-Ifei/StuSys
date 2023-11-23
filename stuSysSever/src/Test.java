import com.alibaba.fastjson2.JSONObject;
import service.ReadOut;
import sever.MessageType;
import sever.Message;
import sever.StudentList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args) {
        StudentList studentList = new StudentList();
        studentList.createdClass();
        String filePath = "database\\" + 2022070343 + ".txt";
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
            for (int i=0;i<studentList.getStuList().size();i++){
                System.out.println(studentList.getStuList().get(i).getStuNum());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}