package producerThread;

import com.ncist.stu.mapper.UserMapper;
import com.ncist.stu.utlis.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import sever.Message;
import sever.MessageType;
import writeIn.WriteIn;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


@SuppressWarnings("all")
public class PPoolReaderRunnable implements Runnable {
    private Socket socket;
    public PPoolReaderRunnable(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }
    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            while (true) {
                ois = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_CREATE)) {
                    System.out.println("Get a message of MESSAGE_CREATE type");
                } else if (message.getMesType().equals(MessageType.MESSAGE_ADD_STU)) {
                    System.out.println("Add "+message.getClassName()+message.getContent());
                    WriteIn.addStu(message.getClassName(),message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CHANGE_STU)){
                    System.out.println("Change "+message.getClassName()+message.getContent());
                    WriteIn.updateStu(message.getClassName(), message.getContent());
                }else if (message.getMesType().equals(MessageType.MESSAGE_DELETE_STU)){
                    System.out.println("Delete "+message.getClassName()+message.getContent());
                    WriteIn.deleteStu(message.getClassName(),message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CREATE_TEST)) {
                    System.out.println("TestInsertORChange "+message.getClassName()+message.getContent());
                    WriteIn.dealTestCreate(message.getClassName(),message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_DELETE_TEST)) {
                    System.out.println("DeleteTest "+message.getClassName());
                    WriteIn.deleteTest(message.getClassName());
                } else {
                    System.out.println(message.getMesType());
                    System.out.println("This is other type message,sever will not do anything...");
                }
            }
        }
        catch (EOFException e){
            try {
                if (ois != null)
                    ois.close();
                if (oos != null)
                    oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client offline");
        }
        catch (SocketException e) {
            try {
                if (ois != null)
                    ois.close();
                if (oos != null)
                    oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client offline");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


