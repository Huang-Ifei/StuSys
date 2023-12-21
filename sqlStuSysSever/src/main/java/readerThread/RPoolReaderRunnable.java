package readerThread;

import com.ncist.stu.mapper.UserMapper;
import com.ncist.stu.utlis.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import readOut.ReadOut;
import sever.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

public class RPoolReaderRunnable implements Runnable {
    private Socket socket;
    public RPoolReaderRunnable(Socket socket) {
        this.socket = socket;
    }
    private final ReadOut readOut = new ReadOut();

    @Override
    public void run() {
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            ois = new ObjectInputStream(this.socket.getInputStream());
            Message message = (Message) ois.readObject();
            if (message.getMesType().equals(MessageType.MESSAGE_READ)) {
                StudentList studentList = readOut.readAClassStu(message.getClassName());
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(studentList);
                oos.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_CLASS)) {
                String classList = readOut.readClasses();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(classList);
                bw.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_BY_ID)){
                Student student = readOut.readById(message.getClassName(),message.getContent());
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(student);
                oos.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_STU_TESTS)) {
                System.out.println("&&&readTests");
                TestPointList testPointList = readOut.readPointById(message.getContent());
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(testPointList);
                oos.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_TESTS)){
                String testList = readOut.readTests();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(testList);
                bw.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_TEST_POINTS)) {
                TestPointList testPointList = readOut.getATestPoint(message.getClassName());
                oos=new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(testPointList);
                oos.close();
            } else {
                System.out.println("Sever get a others type message");
            }
        } catch (SocketException e) {
            System.out.println("Client off");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
