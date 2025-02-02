package decision_tree;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
    public final String BUYING = "buying";
    public final String MAINT = "maint";
    public final String DOORS = "doors";
    public final String PERSONS = "persons";
    public final String LUG_BOOT = "lug_boot";
    public final String SAFETY = "safety";

    public final String CLASS = "class_value";
    public String[] allAttributes;
    public Map<String, Integer> possibleValuesForBuying;
    public Map<String, Integer> possibleValuesForMaint;
    public Map<String, Integer> possibleValuesForDoors;
    public Map<String, Integer> possibleValuesForPersons;
    public Map<String, Integer> possibleValuesForLugBoot;
    public Map<String, Integer> possibleValuesForSafety;

    public Map<String, Integer> possibleValuesForClass;

    public Map<String, Map<String,Integer>> possibleValuesForAny;

    public Attribute(){
        possibleValuesForBuying = new HashMap<>();
        possibleValuesForBuying.put("vhigh",1);
        possibleValuesForBuying.put("high",2);
        possibleValuesForBuying.put("med",3);
        possibleValuesForBuying.put("low",4);

        possibleValuesForMaint=new HashMap<>();
        possibleValuesForMaint.put("vhigh",1);
        possibleValuesForMaint.put("high",2);
        possibleValuesForMaint.put("med",3);
        possibleValuesForMaint.put("low",4);

        possibleValuesForDoors=new HashMap<>();
        possibleValuesForDoors.put("2",1);
        possibleValuesForDoors.put("3",2);
        possibleValuesForDoors.put("4",3);
        possibleValuesForDoors.put("5more",4);

        possibleValuesForPersons=new HashMap<>();
        possibleValuesForPersons.put("2",1);
        possibleValuesForPersons.put("4",2);
        possibleValuesForPersons.put("more",3);

        possibleValuesForLugBoot=new HashMap<>();
        possibleValuesForLugBoot.put("small",1);
        possibleValuesForLugBoot.put("med",2);
        possibleValuesForLugBoot.put("big",3);

        possibleValuesForSafety=new HashMap<>();
        possibleValuesForSafety.put("low",1);
        possibleValuesForSafety.put("med",2);
        possibleValuesForSafety.put("high",3);

        possibleValuesForClass=new HashMap<>();
        possibleValuesForClass.put("unacc",1);
        possibleValuesForClass.put("acc",2);
        possibleValuesForClass.put("good",3);
        possibleValuesForClass.put("vgood",4);

        possibleValuesForAny = new HashMap<>();
        possibleValuesForAny.put(BUYING,possibleValuesForBuying);
        possibleValuesForAny.put(MAINT,possibleValuesForMaint);
        possibleValuesForAny.put(DOORS,possibleValuesForDoors);
        possibleValuesForAny.put(PERSONS, possibleValuesForPersons);
        possibleValuesForAny.put(LUG_BOOT, possibleValuesForLugBoot);
        possibleValuesForAny.put(SAFETY,possibleValuesForSafety);
        possibleValuesForAny.put(CLASS,possibleValuesForClass);

        allAttributes = new String[]{BUYING, MAINT, DOORS, PERSONS, LUG_BOOT, SAFETY};
    }




}
