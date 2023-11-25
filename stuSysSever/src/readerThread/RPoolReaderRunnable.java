package readerThread;

import sever.MessageType;
import sever.Message;
import sever.Student;
import sever.StudentList;
import service.ReadOut;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class RPoolReaderRunnable implements Runnable {
    private Socket socket;

    public RPoolReaderRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            ois = new ObjectInputStream(this.socket.getInputStream());
            Message message = (Message) ois.readObject();
            ReadOut readOut = new ReadOut();
            if (message.getMesType().equals(MessageType.MESSAGE_READ)) {
                StudentList studentList = readOut.readOutAClass(message.getClassName());
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(studentList);
                oos.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_CLASS)) {
                String classList = readOut.readOutClass();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(classList);
                bw.close();
            } else if (message.getMesType().equals(MessageType.MESSAGE_READ_BY_ID)){
                Student student = readOut.readByID(message);
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(student);
                oos.close();
            }else {
                System.out.println("Sever get a others type message");
            }
        } catch (SocketException e) {
            System.out.println("Client off");
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
