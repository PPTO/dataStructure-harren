package algorithm.designPattern.abstractFactotyPattern;

public class XiaomiFactory extends AbstractFactory{
    @Override
    public AbstractPhone2 getPhone() {
        return new Xiaomi();
    }

}
