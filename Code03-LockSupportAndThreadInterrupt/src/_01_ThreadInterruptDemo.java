import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/8 13:58
 * @description: 线程中断面试
 */
public class _01_ThreadInterruptDemo {

    /**
     * 什么是中断机制？
     *  首先：一个线程不应该由其他线程来强制中断或者停止，应该由线程自己进行停止，自己决定自己的命运。
     *  其次：在Java中，没有办法立即停止一条线程，所以Java提供了一种停止线程的协商机制---中断，就是中断标志协商机制。
     *  也就是说调用interrupt就是为线程的中断标识，标记为true，不是真正的中断。
     *  三大方法：
     *      1. interrupt()：中断线程
     *      2. interrupted()：测试当前线程是否中断，同时清除中断状态（静态）
     *      3. isInterrupted()：测试线程是否已被中断
     */
    public static void main(String[] args) {
        //m11();
        //m12();
        //m13();
        //m3();
        m4();
    }
    /**
     * 如何中断一个线程
     */
    static volatile boolean isStop = false;

    //1. 使用volatile进行停止,使用volatile可见性
    static void m11(){
        new Thread(()->{
            while(true){
                if(isStop){
                    System.out.println(Thread.currentThread().getName()+"============stop");
                    break;
                }
                System.out.println("hello volatile");
            }
        },"A").start();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            isStop = true;
        },"B").start();
    }

    //2. 使用AtomicBoolean进行线程停止(原子Boolean值)
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    static void m12(){

        new Thread(()->{
            while(true){
                if(atomicBoolean.get()){
                    System.out.println("中断了========stop");
                    break;
                }
                System.out.println("hello AtomicBoolean");
            }
        },"A").start();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            atomicBoolean.set(true);
        },"B").start();
    }

    //3. 使用Thread自带的API进行实现
    static void m13(){
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("中断了============stop");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " hello API");
            }
        }, "A");
        t1.start();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(() -> {
            t1.interrupt();
        }, "B");
        t2.start();
    }

    /**
     * 如何停止一个线程
     */
    static void m2(){

    }

    /**
     * interrupt和isInterrupt的源码分析
     *      1. interrupt是设置为true(通过interrupt0方法进行标识位true)
     *      2. 调用的是native修饰的方法
     *      3. 如果线程被阻塞，会抛出一个异常，同时退出阻塞，标识位设置位会被修改为false
     */

    static void m3(){
        Thread t1 = new Thread(()->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("退出======stop");
                    break;
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("你好");
            }
        });
        t1.start();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(()->{
            t1.interrupt();
        });
        t2.start();

    }


    /**
     * 测试interrupted方法
     */
    static void m4(){
        System.out.println(""+Thread.interrupted());
        System.out.println(""+Thread.interrupted());
        Thread.currentThread().interrupt();//中断位设置为true
        System.out.println(""+Thread.interrupted());
        System.out.println(""+Thread.interrupted());
    }
}
