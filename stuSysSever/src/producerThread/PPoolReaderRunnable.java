package producerThread;

import sever.MessageType;
import sever.Message;

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
    private ArrayList<Message> arrayList;
    public PPoolReaderRunnable(Socket socket,ArrayList<Message> arrayList) {
        this.arrayList=arrayList;
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
                    System.out.println("Get a message of MESSAGE_READ type");
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("Get"+message.getClassName()+message.getContent());
                    arrayList.add(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CHANGE_STU)){
                    System.out.println("Change"+message.getClassName()+message.getContent());
                    arrayList.add(message);
                }else if (message.getMesType().equals(MessageType.MESSAGE_DELETE_STU)){
                    System.out.println("Delete"+message.getClassName()+message.getContent());
                    arrayList.add(message);
                } else {
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


