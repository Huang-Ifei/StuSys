package writeIn;

import com.alibaba.fastjson2.JSONObject;
import com.ncist.stu.mapper.UserMapper;
import com.ncist.stu.utlis.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class WriteIn {
    public static void addStu(String className,String content){
        System.out.print("&&&addStu "+className+":"+content);
        //导入json
        JSONObject jsonObject = JSONObject.parseObject(content);

        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        boolean haveClass = false;
        //遍历查找是否存在这个class
        List<Map<String,Object>> maps = mapper.getAllClasses();
        for (Map<String, Object> map : maps) {
            if ( map.get("table_name").equals(className))haveClass=true;
        }
        if (haveClass){
            mapper.insertStu(className,jsonObject.getLong("id"),jsonObject.getString("name"), jsonObject.getString("sex"),jsonObject.getString("address"));
        }else {
            mapper.createTable(className);
            mapper.insertStu(className,jsonObject.getLong("id"),jsonObject.getString("name"), jsonObject.getString("sex"),jsonObject.getString("address"));
        }
    }
    public static void deleteStu(String className,String content){
        System.out.print("&&&deleteStu "+className+":"+content);
        JSONObject jsonObject = JSONObject.parseObject(content);
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteById(className,jsonObject.getLong("id"));
    }
    public static void updateStu(String className,String content){
        System.out.print("&&&updateStu "+className+":"+content);
        JSONObject jsonObject = JSONObject.parseObject(content);
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateStu(className,jsonObject.getLong("id"),jsonObject.getString("name"),jsonObject.getString("sex"),jsonObject.getString("address"));
    }
    public static void dealTestCreate(String className,String content){
        JSONObject jsonObject = JSONObject.parseObject(content);
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        if(jsonObject.getDouble("point")==0){
            mapper.deletePoint(className,jsonObject.getLong("id"));
        }else {
            try{
                mapper.insertPoint(className,jsonObject.getLong("id"),jsonObject.getDouble("point"));
            }catch (Exception e){
                try{
                    mapper.changePoint(className,jsonObject.getLong("id"),jsonObject.getDouble("point"));
                }catch (Exception ee){
                    try {
                        mapper.createTest(className);
                        mapper.insertPoint(className,jsonObject.getLong("id"),jsonObject.getDouble("point"));
                    }catch (Exception eee){
                        try {
                            mapper.insertPoint(className,jsonObject.getLong("id"),jsonObject.getDouble("point"));
                        }catch (Exception eeee){
                            System.out.println("!!!Error "+eee);
                        }
                    }
                }
            }
        }
    }
    public static void deleteTest(String className){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteTest(className);
    }
}
