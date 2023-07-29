package algorithm.designPattern.dynamicProxyPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//基于接口的动态代理
public class DynamicProxy {
    public static void main(String[] args) {
        Actor actor = new Actor();
        IActor proxyClass = (IActor)Proxy.newProxyInstance(
                actor.getClass().getClassLoader(),
                actor.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String methodName = method.getName();
                        float arg = (float)args[0];
                        Object invoke = null;
                        if ("getMoney".equals(methodName)){
                            float newArg = arg / 2;
                            invoke = method.invoke(actor, newArg);
                        }

                        return invoke;
                    }
                });
        proxyClass.getMoney(1000);
    }
}
