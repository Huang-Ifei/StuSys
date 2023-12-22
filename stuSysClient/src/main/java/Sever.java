
import com.alibaba.fastjson2.JSONObject;
import com.ncist.stu.sever.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Sever {
    public static String addAStu(String className, long stuNum, String name, String sex,String stuAddress) {
        JSONObject object = new JSONObject();
        object.put("id", stuNum);
        object.put("name", name);
        object.put("sex", sex);
        object.put("address",stuAddress);
        Message message = new Message(MessageType.MESSAGE_ADD_STU, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            br.close();
            oos.writeObject(message);
            oos.close();
        } catch (UnknownHostException e) {
            return "HostError";
        } catch (IOException e) {
            return "Error";
        }
        return "Success";
    }

    public static StudentList getByClass(String className) {
        StudentList studentList = new StudentList();
        studentList.createdClass();
        Message message = new Message(MessageType.MESSAGE_READ, "", className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            studentList = (StudentList) ois.readObject();
            br.close();
            oos.close();
            socket.close();
        } catch (UnknownHostException e) {
            studentList.addStu(0,"HOST错误","","");
        } catch (IOException e) {
            studentList.addStu(0,"IO错误","","");
        } catch (ClassNotFoundException e) {
            studentList.addStu(0,"程序损坏","","");
        }
        return studentList;
    }
    public static List<String> getBasicClasses() {
        List<String> classList = new ArrayList<>();
        classList.add("等待中..");
        return classList;
    }
    public static List<String> getClasses() {
        List<String> classList = new ArrayList<>();
        try {
            Message message = new Message(MessageType.MESSAGE_READ_CLASS, "", "");
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            BufferedReader brS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ( (line = brS.readLine()) != null) {
                classList.add(line);
            }
            br.close();
            oos.close();
            brS.close();
            socket.close();
        }catch (IOException e){
            classList.clear();
            classList.add("IO错误");
        }
        return classList;
    }

    public static String changeAStu(String className, long id, String name,String sex,String stuAddress) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("sex", sex);
        object.put("address",stuAddress);
        Message message = new Message(MessageType.MESSAGE_CHANGE_STU, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            br.close();
            oos.writeObject(message);
            oos.close();
        } catch (UnknownHostException e) {
            return "HostError";
        } catch (IOException e) {
            return "IOError";
        }
        return "Success";
    }
    public static String deleteAStu(String className, long id, String name) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        Message message = new Message(MessageType.MESSAGE_DELETE_STU, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            br.close();
            oos.writeObject(message);
            oos.close();
        } catch (UnknownHostException e) {
            return "HostError";
        } catch (IOException e) {
            return "IOError";
        }
        return "Success";
    }
    public static Student getAStu(long id) {
        Student s = new Student(0,"IO错误","","");;
        JSONObject object = new JSONObject();
        object.put("id", id);
        try {
            Long l = (id/100);
            Message message = new Message(MessageType.MESSAGE_READ_BY_ID, object.toString(),l.toString());
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            s =(Student) ois.readObject();
            br.close();
            oos.close();
            ois.close();
            socket.close();
        }catch (IOException e){
            return s;
        } catch (ClassNotFoundException e) {
            return new Student(0,"程序损坏！","","");
        }
        return s;
    }
    public static List<String> getTests(){
        List<String> testList = new ArrayList<>();
        try {
            Message message = new Message(MessageType.MESSAGE_READ_TESTS, "", "");
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            BufferedReader brS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ( (line = brS.readLine()) != null) {
                testList.add(line);
            }
            br.close();
            oos.close();
            brS.close();
            socket.close();
        }catch (IOException e){
            testList.clear();
            testList.add("IO错误");
        }
        return testList;
    }
    public static TestPointList getTestPoints(String testName){
        TestPointList testPointList = new TestPointList();
        if (testName.equals("选择成绩表")||testName.equals("添加一个新测试")){
            return testPointList;
        }
        try {
            Message message = new Message(MessageType.MESSAGE_READ_TEST_POINTS, "", testName);
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            String host = br.readLine();
            br.close();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            testPointList=(TestPointList)ois.readObject();
            oos.close();
            ois.close();
            socket.close();
        } catch (UnknownHostException e) {
            testPointList.add(testName,new TestPoint(1001,0));
        } catch (FileNotFoundException e) {
            testPointList.add(testName,new TestPoint(1002,0));
        } catch (IOException e) {
            testPointList.add(testName,new TestPoint(1003,0));
        } catch (ClassNotFoundException e) {
            testPointList.add(testName,new TestPoint(1004,0));
        }
        return testPointList;
    }
    //public static TestPointList getTestPointListById(long id){

    //}
    public static String saveTestPoint(TestPointList testPointList,String testName){
        String line;
        String host;
        InetAddress address;
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            line = br.readLine();
            br.readLine();
            host = br.readLine();
            br.close();
            address = InetAddress.getByName(host);
        }catch (Exception e){
            return "程序可能损坏";
        }
        try{
            for(TestPoint testPoint : testPointList.getTestPointList()){
                if (testPoint.getId()!=0L){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",testPoint.getId());
                    jsonObject.put("point",testPoint.getPoint());
                    Message message = new Message(MessageType.MESSAGE_CREATE_TEST, jsonObject.toString(),testName);
                    Socket socket = new Socket(address, Integer.parseInt(line));
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                    oos.close();
                }
            }
        }catch (Exception e){
            return e.toString();
        }
        return "提交成功";
    }
    public static TestPointList getAStuTest(long id){
        TestPointList testPointList = new TestPointList();
        String line;
        String host;
        InetAddress address;
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            br.readLine();
            line = br.readLine();
            host = br.readLine();
            br.close();
            address = InetAddress.getByName(host);
        }catch (Exception e){
            testPointList.add("程序可能损坏",new TestPoint());
            return testPointList;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        Message message = new Message(MessageType.MESSAGE_READ_STU_TESTS, jsonObject.toString(),"");
        try {
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            testPointList=(TestPointList)ois.readObject();
            oos.close();
            ois.close();
            socket.close();
        }catch (Exception e){
            testPointList.add("发送失败",new TestPoint());
            return testPointList;
        }
        return testPointList;
    }
    public static String deleteTest(String className) {
        Message message = new Message(MessageType.MESSAGE_DELETE_TEST, "", className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            br.readLine();
            String host = br.readLine();
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            br.close();
            oos.writeObject(message);
            oos.close();
        } catch (UnknownHostException e) {
            return "错误HostError";
        } catch (IOException e) {
            return "错误IOError";
        }
        return "删除成功";
    }
}
