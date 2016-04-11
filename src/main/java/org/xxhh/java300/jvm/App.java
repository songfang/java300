package org.xxhh.java300.jvm;

/**
 * Runtime的addShutdownHook方法可以让程序在退出时有机会执行退出代码. 请注意有机会三个字，表示不是所有时候都可以执行，
 * 在程序正常退出时可以执行，但是在用户通过kill -9杀死程序时，addShutdownHook中的代码是没有执行机会的。
 * @author sf
 *
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("I am shutdown hook");
            }
        }));
        
        int i = 10;
        while (i>0) {
        	--i;
            System.out.println("I am living...");
            Thread.sleep(1000);
        }
    }
}