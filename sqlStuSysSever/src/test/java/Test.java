import com.ncist.stu.mapper.UserMapper;
import com.ncist.stu.utlis.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import sever.Message;
import sever.MessageType;
import sever.TestPoint;
import sever.TestPointList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String []args){
        TestPointList testPointList = new TestPointList();
        try {
            SqlSession sqlSession = SqlSessionUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.deleteTest("2022C语言");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
