package algorithm.designPattern.produceConsumePattern;

import java.util.ArrayList;

public class Application {

    private static ArrayList<String> mq = new ArrayList<>();
    public static void main(String[] args) {

        Runnable production = ()->{
           synchronized (mq){
               mq.add("this is a message from " + Thread.currentThread().getName());
               mq.notify();
           }
        };
        Runnable consumer = ()->{
          while (true){
              synchronized (mq){
                  while (mq.size() == 0){
                      try {
                          mq.wait();
                      } catch (InterruptedException e) {
                          throw new RuntimeException(e);
                      }
                  }
                  String s = mq.remove(mq.size() - 1);
                  System.out.println("this is consumer " + Thread.currentThread().getName() + " dealing \"" + s + "\"");
              }
          }
        };
        for (int i = 0; i < 2; i++) {
            new Thread(consumer).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(production).start();
        }


    }
}
