package decision_tree;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataSetOfCars {
    public Set<Car> carList;
    //public int numberOfUnAcc, numberOfAcc, numberOfGood, numberOfVGood;
    public int[] numbersOfTargetAttribute;

    public DataSetOfCars(){
        carList=new LinkedHashSet<>();
        //carList=new HashSet<>();
        //numberOfAcc=numberOfUnAcc=numberOfGood=numberOfVGood=0;
        numbersOfTargetAttribute=new int[5];
    }
}
