package com.ncist.stu.thread;

import com.ncist.stu.readOut.ReadOut;
import com.ncist.stu.sever.*;
import com.ncist.stu.writeIn.WriteIn;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ThreadRunnable implements Runnable {
    private Socket socket;

    public ThreadRunnable(Socket socket) {
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
                ReadOut readOut = new ReadOut();
                ois = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_CREATE)) {
                    System.out.println("Get a message of MESSAGE_CREATE type");
                } else if (message.getMesType().equals(MessageType.MESSAGE_ADD_STU)) {
                    System.out.println("Add " + message.getClassName() + message.getContent());
                    WriteIn.addStu(message.getClassName(), message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CHANGE_STU)) {
                    System.out.println("Change " + message.getClassName() + message.getContent());
                    WriteIn.updateStu(message.getClassName(), message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_DELETE_STU)) {
                    System.out.println("Delete " + message.getClassName() + message.getContent());
                    WriteIn.deleteStu(message.getClassName(), message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CREATE_TEST)) {
                    System.out.println("TestInsertORChange " + message.getClassName() + message.getContent());
                    WriteIn.dealTestCreate(message.getClassName(), message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_DELETE_TEST)) {
                    System.out.println("DeleteTest " + message.getClassName());
                    WriteIn.deleteTest(message.getClassName());
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ)) {
                    StudentList studentList = readOut.readAClassStu(message.getClassName());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(studentList);
                    oos.close();
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_CLASS)) {
                    String classList = readOut.readClasses();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(classList);
                    bw.close();
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_BY_ID)) {
                    Student student = readOut.readById(message.getClassName(), message.getContent());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(student);
                    oos.close();
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_STU_TESTS)) {
                    System.out.println("&&&readTests");
                    TestPointList testPointList = readOut.readPointById(message.getContent());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(testPointList);
                    oos.close();
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_TESTS)) {
                    String testList = readOut.readTests();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(testList);
                    bw.close();
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_TEST_POINTS)) {
                    TestPointList testPointList = readOut.getATestPoint(message.getClassName());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(testPointList);
                    oos.close();
                } else {
                    System.out.println(message.getMesType());
                    System.out.println("This is other type message,sever will not do anything...");
                }
            }
        } catch (EOFException e) {
            try {
                if (ois != null)
                    ois.close();
                if (oos != null)
                    oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client offline");
        } catch (SocketException e) {
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
