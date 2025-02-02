import decision_tree.Attribute;
import decision_tree.Car;
import decision_tree.DataSetOfCars;
import decision_tree.DecisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DataManagement{
    public static String sep = ",";
    public static Attribute attributeInfo = new Attribute();

//    public static String[] getAttributesFromData(String filePath){
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            return br.readLine().split(sep);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public static DataSetOfCars readTrainData(String filePath){
        //Set<Car> data = new HashSet<>();
        DataSetOfCars dataSetOfCars = new DataSetOfCars();
        int i=1;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; // remove first heading line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(sep);
               // System.out.println(values[6]);
                try{
                    Car p = new Car(i++);
                    p.setBuying(values[0]).setMaint(values[1]).setDoors(values[2]).setPersons(values[3]).setLugBoot(values[4]).setSafety(values[5]).setClass(values[6]);

                    dataSetOfCars.carList.add(p);
                    Integer targetVal = attributeInfo.possibleValuesForClass.get(values[6]);
                    dataSetOfCars.numbersOfTargetAttribute[targetVal]++;
                }catch(IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSetOfCars;
    }

    public static void printData(DataSetOfCars dataSetOfCars){
        Object[] dataForCars = dataSetOfCars.carList.toArray();

        Car c;
        for(int i=0;i<dataForCars.length;i++){
            c = (Car) dataForCars[i];
            System.out.println("id: "+c.id+" ,"+c.attributesInString.get(attributeInfo.BUYING)+" ,"+c.attributesInString.get(attributeInfo.MAINT)+", "+c.attributesInString.get(attributeInfo.DOORS)+", "+c.attributesInString.get(attributeInfo.PERSONS)+", "+c.attributesInString.get(attributeInfo.LUG_BOOT)+", "+c.attributesInString.get(attributeInfo.SAFETY)+" ,"+c.attributesInString.get(attributeInfo.CLASS));
        }
        for(int i=0; i< dataSetOfCars.numbersOfTargetAttribute.length;i++){
            System.out.print(dataSetOfCars.numbersOfTargetAttribute[i]+" ");
        }
        System.out.println();

    }

    public static DataSetOfCars[] dividerOfData(DataSetOfCars dataSetOfCars, int percent){
        int sepIndex = (int) (dataSetOfCars.carList.size() * (double) percent / 100.0);

        Object[] carData = dataSetOfCars.carList.toArray();

        DataSetOfCars[] carDataLists = new DataSetOfCars[2];
        carDataLists[0] = new DataSetOfCars();
        carDataLists[1] = new DataSetOfCars();
        for(int i=0;i<sepIndex;i++){
            Car car = (Car) carData[i];
            carDataLists[0].carList.add(car);
            carDataLists[0].numbersOfTargetAttribute[car.attributes.get(attributeInfo.CLASS)]++;

        }
        for(int i=sepIndex;i<carData.length;i++){
            Car car = (Car) carData[i];
            carDataLists[1].carList.add(car);

            carDataLists[1].numbersOfTargetAttribute[car.attributes.get(attributeInfo.CLASS)]++;
        }

        return carDataLists;
    }

}
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        String filePathForData = "src/car.data";
        //Set<Car> carData;
        DataSetOfCars dataSetOfCars;
        dataSetOfCars=DataManagement.readTrainData(filePathForData);
        double[] arrayOfNumbers = new double[20];

        for(int i=0;i<20;i++) {
            List<Car> tempList = new ArrayList<>(dataSetOfCars.carList);

            Collections.shuffle(tempList);
            dataSetOfCars.carList = new LinkedHashSet<>(tempList);


            DataSetOfCars[] temp = DataManagement.dividerOfData(dataSetOfCars, 80);
            DataSetOfCars trainingData = temp[0];
            DataSetOfCars evaluatingData = temp[1];


            //DataManagement.printData(trainingData);

            Attribute attributeObj = new Attribute();
            DecisionTree decisionTree = new DecisionTree(attributeObj.CLASS, attributeObj.allAttributes);


            decisionTree.train(trainingData);


            Integer[] results = decisionTree.Classify(evaluatingData);
            //Integer[] results = decisionTree.Classify(dataSetOfCars);

            System.out.println("Matched: " + results[1]);
            System.out.println("Not Matched: " + results[0]);
            double percentageOfMatch = (double) results[1] / (double) (results[0] + results[1]) * 100;
            System.out.println("Percentage of matched: " + percentageOfMatch + " %");
            arrayOfNumbers[i] = percentageOfMatch;
        }

        double sum = Arrays.stream(arrayOfNumbers).sum();
        double mean = sum / arrayOfNumbers.length;

        double sumOfSquaredDifferences = Arrays.stream(arrayOfNumbers).map(x -> Math.pow(x - mean, 2)).sum();
        double sd = Math.sqrt(sumOfSquaredDifferences / arrayOfNumbers.length);
        System.out.println("===================================================================================");
        //System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Mean Accuracy: "+mean+" %");
        System.out.println("Standard Deviation :"+sd);

    }


}