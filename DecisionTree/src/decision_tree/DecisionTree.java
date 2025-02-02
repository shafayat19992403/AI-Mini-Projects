package decision_tree;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DecisionTree {
    public Node root;
    public String targetAttribute;
    public String[] trainAttributes;
    public Attribute attributeObject;

    public DecisionTree(String targetAttribute,String[] trainAttributes){
        this.targetAttribute=targetAttribute;
        this.trainAttributes=trainAttributes;
        attributeObject = new Attribute();
        root=new Node();
    }

    public void train(DataSetOfCars data){
        train(this.root, data, this.trainAttributes);
    }
    public void train(Node node,DataSetOfCars carData, String[] currentAvailableAttributes){
           Integer posVal = IsAnyAllPositive(carData);
           if(posVal != -1){
               node.label = String.valueOf(posVal);
               node.isLeaf = true;
           }else if(currentAvailableAttributes.length == 0){
               node.label = String.valueOf(mostCommonTargetValue(carData));
               node.isLeaf = true;
           }else{
                String bestAttribute = getBestSplitAttribute(carData, currentAvailableAttributes);
                node.label = new String(bestAttribute);
                for(int value : attributeObject.possibleValuesForAny.get(bestAttribute).values()){
                    ConditionalDecision cond = new ConditionalDecision(value);
                    Node child = new Node();
                    cond.successor = child;
                    node.conditionalDecisions.add(cond);
                    DataSetOfCars subSet = createSubset(carData, bestAttribute, value);
                    if(subSet.carList.isEmpty()){
                        child.label = new String(String.valueOf(mostCommonTargetValue(carData)));
                        child.isLeaf = true;
                    }else{
                        String[] remainingAttributes = removeAttribute(bestAttribute,currentAvailableAttributes);
                        train(child, subSet, remainingAttributes);
                    }
                }
           }
    }

    public String[] removeAttribute(String attributeToBeRemoved,  String[] currentlyAvailableAttributes){
        int currSize = currentlyAvailableAttributes.length;
        if(currSize==0) return null;

        String[] remainingAttributes = new String[currSize - 1];
        int j = 0;
        for(int i=0; i<currentlyAvailableAttributes.length; i++){
            if(currentlyAvailableAttributes[i].equalsIgnoreCase(attributeToBeRemoved)){
                continue;
            }
            remainingAttributes[j] = currentlyAvailableAttributes[i];
            j++;
        }
        return remainingAttributes;
    }
    public Integer mostCommonTargetValue(DataSetOfCars dataSetOfCars){
            int arr[] = dataSetOfCars.numbersOfTargetAttribute;
            int max = -1;
            int tag = -1;
            for(int i = 1; i<arr.length;i++){
                if(arr[i] > max) {
                    max = arr[i];
                    tag = i;
                }
            }
            return tag;

    }


    public Integer IsAnyAllPositive(DataSetOfCars carData){
        int arr[] = carData.numbersOfTargetAttribute;

        if(arr[1] != 0 && arr[2] == 0 && arr[3] == 0 && arr[4] == 0) return new Integer(1);
        else if(arr[1] == 0 && arr[2] != 0 && arr[3] == 0 && arr[4] == 0) return new Integer(2);
        else if(arr[1] == 0 && arr[2] == 0 && arr[3] != 0 && arr[4] == 0) return new Integer(3);
        else if(arr[1] == 0 && arr[2] == 0 && arr[3] == 0 && arr[4] != 0) return new Integer(4);
        else return new Integer(-1);
    }


    private double logTwo(double x){
        return Math.log(x)/Math.log(2.0);
    }

    public String getBestSplitAttribute(DataSetOfCars dataSetOfCars,String[] currentAvailableAttributes){
        double[] informationGainForAllAttributes = new double[currentAvailableAttributes.length];

        for(int i=0; i<currentAvailableAttributes.length;i++){
            informationGainForAllAttributes[i] = calculateInformationGain(dataSetOfCars, currentAvailableAttributes[i]);

        }

        int bestAttribute = getMaxIndexInAnArray(informationGainForAllAttributes);
        return currentAvailableAttributes[bestAttribute];
    }
    private double calculateEntropy(DataSetOfCars dataSetOfCars,String attribute){
        double entropy = 0;
        DataSetOfCars subset;
        double p;
        for(int value : attributeObject.possibleValuesForAny.get(attribute).values()){
            subset = createSubset(dataSetOfCars, attribute, value);
            if(subset.carList.size() > 0){
                p = (double) subset.carList.size() / (double) dataSetOfCars.carList.size();
                entropy -= p*logTwo(p);
            }
        }
        return entropy;
    }
    private double calculateInformationGain(DataSetOfCars dataSetOfCars,String attribute){
        double preEntropy = calculateEntropy(dataSetOfCars, this.targetAttribute);
        double postEntropy = 0.0;

        for(int value : attributeObject.possibleValuesForAny.get(attribute).values()){
            DataSetOfCars subset = createSubset(dataSetOfCars,attribute, value);
            double weight = (double) subset.carList.size() / (double) dataSetOfCars.carList.size();
            double subsetEntropy = calculateEntropy(subset, this.targetAttribute);
            postEntropy += weight*subsetEntropy;
        }
        return preEntropy - postEntropy;
    }

    private int getMaxIndexInAnArray(double[] arr){
        int index = 0;
        double maxValue = Double.MIN_VALUE;
        for(int i=0; i<arr.length;i++){
            if(arr[i]>maxValue){
                maxValue = arr[i];
                index = i;
            }
        }
        return index;
    }
    public DataSetOfCars createSubset(DataSetOfCars mainSet,String attribute,int value){
        DataSetOfCars subSet = new DataSetOfCars();
        Object[] dataArr = mainSet.carList.toArray();
        //System.out.println(dataArr.length);
        Car c;
        for(Object obj : dataArr){
            c = (Car) obj;
            //if(c==null) System.out.println("Fuck");
//            System.out.println("-----------------");
//            for(String str : c.attributesInString.values()) System.out.println(str);
            if(c.attributes.get(attribute) == value){
                subSet.carList.add(c);
               subSet.numbersOfTargetAttribute[c.attributes.get(attributeObject.CLASS)]++;
                //System.out.println(attributeObject.CLASS);

            }
        }
        return subSet;
    }

    public String Classify(Car car){
        Node currNode = root;
        List<ConditionalDecision> branches;

        while(!currNode.isLeaf){
            //System.out.print(currNode.label+" ");
            branches = currNode.conditionalDecisions;
            int compareValue = car.attributes.get(currNode.label);
            Node next = null;
            for(ConditionalDecision branch : branches){
                if(branch.check(compareValue)){
                    next = branch.successor;
                    break;
                }
            }

            if(next == null){
                return "-1";
            }else{
                currNode = next;
            }
        }
        //System.out.println(currNode.label);
        return currNode.label;
    }

    public Integer[] Classify(DataSetOfCars dataSetOfCars){
        Object[] carList =  dataSetOfCars.carList.toArray();
        Integer[] results= {0,0};

        for(int i=0;i<carList.length;i++){
            Car car = (Car) carList[i];
            String temp = Classify(car);
            Integer ans = car.attributes.get(attributeObject.CLASS);
            if(temp.equalsIgnoreCase(String.valueOf(ans))){
                results[1]++;
            }else{
                results[0]++;
            }
        }
        return results;
    }

}
