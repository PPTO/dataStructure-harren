package algorithm.designPattern.AdapterPattern;

public class Adapter extends Adaptee implements Target{
    @Override
    public void request(String s) {
        this.specificRequest(s);
    }
}
