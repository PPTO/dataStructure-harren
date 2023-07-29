package algorithm.designPattern.singletonPattern;

/**
 * double check lock
 * 懒汉式
 */
public class SingletonPattern {
    private volatile static SingletonPattern instance;

    private String name;

    private SingletonPattern(String name){
        this.name = name;
    }

    public static SingletonPattern getInstance(String name){
        if (instance == null){
            synchronized (SingletonPattern.class){
                if (instance == null){
                    instance = new SingletonPattern(name);
                }
            }
        }
        return instance;
    }



}
