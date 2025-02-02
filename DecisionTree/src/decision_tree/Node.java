package decision_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class Node {
    public String label;
    public boolean isLeaf;
    public List<ConditionalDecision> conditionalDecisions;

    public Node(){
        conditionalDecisions = new ArrayList<>();
        isLeaf=false;
        label="NOT USED YET";
    }

}
