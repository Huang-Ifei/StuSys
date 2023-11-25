package service;

import com.alibaba.fastjson2.JSONObject;
import sever.Message;
import sever.Student;

import java.io.*;
import java.util.ArrayList;

public class DataChange {
    public DataChange(Message message){
        String filePath = "database\\"+ message.getClassName();
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line=br.readLine())!=null){
                JSONObject json = JSONObject.parseObject(line);
                if (json.get("stuNum").equals(JSONObject.parseObject(message.getContent()).get("stuNum"))){
                    list.add(message.getContent());
                }else {
                    list.add(line);
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,false));
            for (int i=0;i<list.size();i++){
                bw.write(list.get(i)+"\n");
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
