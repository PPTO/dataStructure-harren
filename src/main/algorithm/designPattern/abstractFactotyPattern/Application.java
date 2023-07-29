package algorithm.designPattern.abstractFactotyPattern;

import algorithm.designPattern.abstractFactotyPattern.AbstractFactory.AbstractPhone2;
public class Application {
    public static void main(String[] args) {

        AbstractFactory factory = new XiaomiFactory();
        AbstractPhone2 phone2 = factory.getPhone();
        String s = phone2.phone();
        System.out.println(s);

    }
}
