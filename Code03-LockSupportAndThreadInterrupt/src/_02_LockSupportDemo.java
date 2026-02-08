import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/9 15:23
 * @description: LockSupport工具
 */
public class _02_LockSupportDemo {
    public static void main(String[] args) {
        //m1();
        //m2();
        m3();
    }

    /**
     * LockSupport是什么？
     *  它是在JUC包中的一个类，用于创建锁和其他同步类的基本线程阻塞原语(静态类)
     *  具体方法：
     *      1. getBlocker(Thread t)：                    返回提供给尚未解除阻塞的park方法的最新调用的阻止程序对象，如果未阻止，返回null
     *      2. park()：                                  除非许可证允许，否则禁用当前线程以进行线程调用
     *      3. park(Object blocker)：                    除非许可证允许，否则禁用当前线程以进行线程调用
     *      4. parkNanos(long nano)：                    除非许可证允许，否则禁用当前线程以进行线程调用，直到指定的时间
     *      5. parkNanos(Object,blocker,long nano)：     除非许可证允许，否则禁用当前线程以进行线程调用，直到指定的时间
     *      6. parkUtil(long deadline)：                 除非许可证允许，否则禁用当前线程以进行线程调用，直到指定的时间
     *      7. parkUtils(Object blocker,long deadline)： 除非许可证允许，否则禁用当前线程以进行线程调用，直到指定的时间
     *      8. unpark(Thread t)：                        如果给定线程不可用，为其提供许可
     */

    /**
     * 线程停止与唤醒机制
     *      1. 使用Object中的wait()方法让线程等待，使用Object中的ontify()方法唤醒线程
     *      2. 使用JUC包中的Condition的await()方法让线程阻塞，使用signal()方法进行幻想
     *      3. LockSupport中的可以对当前线程进行阻塞或者唤醒。（包括指定的线程）
     */

    /**
     * 方法1:
     *      wait和notify要在synchronized中
     *      同样要先wait再notify才Ok
     */
    static void m1(){
        Object o = new Object();
        new Thread(()->{
            synchronized (o){
                System.out.println(Thread.currentThread().getName()+"=============== come in");
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"被唤醒");
            }
        },"A").start();

        try {
            sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            synchronized (o){
                o.notify();
                System.out.println(Thread.currentThread().getName()+"发出通知");
            }
        },"B").start();
    }

    /**
     * 方法2
     *      使用JUC包中的Condition的await()方法让线程阻塞，使用signal()方法进行唤醒
     *      同样await()和Signal()同样要存在lock中才能进行调用
     *      同样要先await再nSignal才Ok
     */
    static void m2(){
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(()->{
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"=============== come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        },"A").start();

        try {
            sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            reentrantLock.lock();
            try{
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"发出通知");
            }finally {
                reentrantLock.unlock();
            }

        },"B").start();
    }

    /**
     * 方法3
     *      LockSupport中的可以对当前线程进行阻塞或者唤醒。（包括指定的线程）
     *      Locksupport使用的是许可证，同时只有一个许可证
     *      park和unpark一起使用
     *      park需要消耗两个凭证
     *      unpark只生成一个，且最多为1个
     *
     *      所以两次park,两次unpark，还是会阻塞
     *
     */
    static void m3(){
        Thread A = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "=============== come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        }, "A");
        A.start();

        try {
            sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            LockSupport.unpark(A);//直接发许可证
            System.out.println(Thread.currentThread().getName()+"发出通知");
        },"B").start();
    }
}
