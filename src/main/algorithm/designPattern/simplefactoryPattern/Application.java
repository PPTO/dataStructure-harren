package algorithm.designPattern.simplefactoryPattern;

import algorithm.designPattern.simplefactoryPattern.SimpleFactory.AbstractPhone;
public class Application {
    public static void main(String[] args) {

        AbstractPhone phone = SimpleFactory.getPhone("xiaomi");
        String phone1 = phone.phone();
        System.out.println(phone1);


    }
}
