package decision_tree;

public class ConditionalDecision {
    public Node successor;
    public int value;
    public ConditionalDecision(int value){
        this.value=value;
    }
    public void setSuccessor(Node successor){
        this.successor=successor;
    }
    public Node getSuccessor(){
        return successor;
    }
    public boolean check(int value){
        return this.value == value;
    }
}
