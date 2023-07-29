package algorithm.designPattern.singletonPattern;

/**
 * 静态内部类实现懒汉式单例模式
 */
public class SingletonParttern2 {

    private static class Singleton{
        private static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance(){
        return Singleton.instance;
    }

}
