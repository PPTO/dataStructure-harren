package algorithm.designPattern.AdapterPattern;

public class Adaptee {
    public void specificRequest(String s){
        System.out.println("This is a result from specificRequest: " + s);
    }
}
