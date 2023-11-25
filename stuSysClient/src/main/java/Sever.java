
import com.alibaba.fastjson2.JSONObject;
import sever.Message;
import sever.MessageType;
import sever.Student;
import sever.StudentList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Sever {
    public static String addAStu(String className, long stuNum, String name, double point) {
        JSONObject object = new JSONObject();
        object.put("stuNum", stuNum);
        object.put("name", name);
        object.put("point", point);
        Message message = new Message(MessageType.MESSAGE_COMM_MES, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            InetAddress address = InetAddress.getByName("Huang_Ifei");
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

    public static StudentList getByClass(String className) {
        StudentList studentList;
        Message message = new Message(MessageType.MESSAGE_READ, "", className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            InetAddress address = InetAddress.getByName("Huang_Ifei");
            Socket socket = new Socket(address, Integer.parseInt(line));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            studentList = (StudentList) ois.readObject();
            br.close();
            oos.close();
            socket.close();
        } catch (UnknownHostException e) {
            studentList = new StudentList();
            studentList.createdClass();
            studentList.addStu(0,"HOST错误",0);
        } catch (IOException e) {
            studentList = new StudentList();
            studentList.createdClass();
            studentList.addStu(0,"IO错误",0);
        } catch (ClassNotFoundException e) {
            studentList = new StudentList();
            studentList.createdClass();
            studentList.addStu(0,"ClassNotFound错误",0);
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
            InetAddress address = InetAddress.getByName("Huang_Ifei");
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

    public static String changeAStu(String className, long stuNum, String name, double point) {
        JSONObject object = new JSONObject();
        object.put("stuNum", stuNum);
        object.put("name", name);
        object.put("point", point);
        Message message = new Message(MessageType.MESSAGE_CHANGE_STU, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            InetAddress address = InetAddress.getByName("Huang_Ifei");
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
    public static String deleteAStu(String className, long stuNum, String name, double point) {
        JSONObject object = new JSONObject();
        object.put("stuNum", stuNum);
        object.put("name", name);
        object.put("point", point);
        Message message = new Message(MessageType.MESSAGE_DELETE_STU, object.toString(), className);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            line = br.readLine();
            InetAddress address = InetAddress.getByName("Huang_Ifei");
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
    public static Student getAStu(long stuNum) {
        Student s = new Student(0,"IO错误",0.0);;
        JSONObject object = new JSONObject();
        object.put("stuNum", stuNum);
        try {
            Long l = (stuNum/100);
            Message message = new Message(MessageType.MESSAGE_READ_BY_ID, object.toString(),l.toString());
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\sever\\IP"));
            String line;
            br.readLine();
            line = br.readLine();
            InetAddress address = InetAddress.getByName("Huang_Ifei");
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
            return new Student(0,"程序损坏！",0.0);
        }
        return s;
    }
}
