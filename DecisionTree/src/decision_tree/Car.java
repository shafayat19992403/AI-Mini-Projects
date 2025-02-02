package decision_tree;

import com.sun.tools.javac.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Car {
    public int id;
    public Map<String,Integer> attributes;
    public Map<String,String> attributesInString;
    public static Attribute attributeInfo=new Attribute();

    public Car(int id){
        this.id=id;
        attributes=new HashMap<>();
        attributesInString=new HashMap<>();
    }

    public Car setBuying(String buying){
        //System.out.println();
        attributes.put(attributeInfo.BUYING, attributeInfo.possibleValuesForBuying.get(buying));
        attributesInString.put(attributeInfo.BUYING, buying);
        return this;
    }

    public Car setMaint(String maint){
        attributes.put(attributeInfo.MAINT,attributeInfo.possibleValuesForMaint.get(maint));
        attributesInString.put(attributeInfo.MAINT, maint);
        return this;
    }

    public Car setDoors(String doors){
        attributes.put(attributeInfo.DOORS, attributeInfo.possibleValuesForDoors.get(doors));
        attributesInString.put(attributeInfo.DOORS, doors);
        return this;
    }

    public Car setPersons(String persons){
        attributes.put(attributeInfo.PERSONS, attributeInfo.possibleValuesForPersons.get(persons));
        attributesInString.put(attributeInfo.PERSONS,persons);
        return this;
    }

    public Car setLugBoot(String str){
        attributes.put(attributeInfo.LUG_BOOT, attributeInfo.possibleValuesForLugBoot.get(str));
        attributesInString.put(attributeInfo.LUG_BOOT,str);
        return this;
    }

    public Car setSafety(String str){
        attributes.put(attributeInfo.SAFETY, attributeInfo.possibleValuesForSafety.get(str));
        attributesInString.put(attributeInfo.SAFETY,str);
        return this;
    }

    public Car setClass(String str){
        attributes.put(attributeInfo.CLASS, attributeInfo.possibleValuesForClass.get(str));
        attributesInString.put(attributeInfo.CLASS,str);
        return this;
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        //Person person = (Person) obj;
//        Car car = (Car) obj;
//        //return age == person.age && name.equals(person.name);
//        return attributes.get(attributeInfo.CLASS) == car.attributes.get(attributeInfo.CLASS);
//    }
//
//    @Override
//    public int hashCode() {
//       // return Objects.hash(name, age);
//        return Objects.hash(attributes.get(attributeInfo.CLASS).intValue(),attributesInString.get(attributeInfo.CLASS));
//    }


}
