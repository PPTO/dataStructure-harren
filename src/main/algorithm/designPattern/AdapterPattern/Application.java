package algorithm.designPattern.AdapterPattern;

public class Application {
    public static void main(String[] args) {
        Target target = new Adapter();
        target.request("fuck you!");
    }
}
