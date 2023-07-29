package algorithm.designPattern.abstractFactotyPattern;

public class IphoneFactory extends AbstractFactory{
    @Override
    public AbstractPhone2 getPhone() {
        return new Iphone();
    }
}
