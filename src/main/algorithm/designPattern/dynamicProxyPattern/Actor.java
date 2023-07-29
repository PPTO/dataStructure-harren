package algorithm.designPattern.dynamicProxyPattern;

public class Actor implements IActor {
    @Override
    public void getMoney(float money) {
        System.out.println("拿到了" + money + "钱");
    }
}
