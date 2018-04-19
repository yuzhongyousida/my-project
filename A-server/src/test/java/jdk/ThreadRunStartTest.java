package jdk;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/19
 */
public class ThreadRunStartTest {

    public static void main(String[] args) {
        test1();

        try {
            Thread.sleep(4000);
            System.out.println("---------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test2();

    }


    private static void test1(){
        System.out.println("test1()方法执行start-----");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("test1 - Runnable.run()方法执行-----");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        r.run();

        System.out.println("test1()方法执行end-----");

    }

    private static void test2(){
        System.out.println("test2()方法执行start-----");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("test2 - Runnable.run()方法执行-----");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();

        System.out.println("test2()方法执行end-----");

    }

}
