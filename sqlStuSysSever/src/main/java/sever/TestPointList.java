package sever;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestPointList implements Serializable {
    private static final long serialVersionUID = 1;
    private List<TestPoint> testPointList = new ArrayList<>();
    public TestPointList(){

    }
    public void add(String testName,TestPoint testPoint){
        testPoint.setTest(testName);
        testPointList.add(testPoint);
    }

    public List<TestPoint> getTestPointList() {
        return testPointList;
    }
    public void clear(){
        testPointList.clear();
    }
}
