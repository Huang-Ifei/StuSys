package service;

import sever.Message;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class WriteIn {
    public WriteIn(ArrayList<Message> writeInList,String className){
        String writeFilePath = "database\\"+className;
        String data = "";
        for (Message wm:writeInList){
            data+=wm.getContent()+"\n";
        }
        //写入
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFilePath,true));
            bos.write(data.getBytes());
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
