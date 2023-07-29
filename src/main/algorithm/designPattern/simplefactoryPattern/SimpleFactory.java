package algorithm.designPattern.simplefactoryPattern;

public class  SimpleFactory {

    public static AbstractPhone getPhone(String phoneName){
        if ("xiaomi".equalsIgnoreCase(phoneName)){
            return new Xiaomi();
        }
        else if ("iphone".equalsIgnoreCase(phoneName)){
            return new Iphone();
        }
        return null;
    }
    public interface AbstractPhone{
        public String phone();
    }
    private static class Xiaomi implements AbstractPhone {
        @Override
        public String phone() {
            return "xiaomi";
        }
    }
    private static class Iphone implements AbstractPhone{
        @Override
        public String phone() {
            return "iphone";
        }
    }
}
