package algorithm.designPattern.abstractFactotyPattern;

public abstract class AbstractFactory{

    public abstract AbstractPhone2 getPhone();


    protected interface AbstractPhone2 {
        public String phone();
    }
    protected class Xiaomi implements AbstractPhone2 {
        @Override
        public String phone() {
            return "xiaomi";
        }
    }
    protected class Iphone implements AbstractPhone2 {
        @Override
        public String phone() {
            return "iphone";
        }
    }
}
