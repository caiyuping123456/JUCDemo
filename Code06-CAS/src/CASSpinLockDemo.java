import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/14 15:14
 * @description: 业务
 */
public class CASSpinLockDemo {

    public static void main(String[] args) {
        //m2();
        m3();
    }
    /**
     * 基于CAS写自旋锁
     */

    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    //自旋加锁
    public void lock(){
        Thread thread = Thread.currentThread();
        //null表示没有线程占用
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }
    //自旋解锁
    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
    }

    /**
     * CAS的缺点
     *      1. 循环时间长了会导致时间开销很大
     *      2. 会导致ABA问题（中间过程不安全）
     */

    /**
     * 解决ABA问题：
     *      1. 添加版本号，戳记流水
     *      2.
     */

    //邮戳类，就是比较版本号
    static void m1(){
        Book book = new Book();
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(book,1);
        Book reference = atomicStampedReference.getReference();//类
        int stamp = atomicStampedReference.getStamp();//版本

        Book Mysql = new Book();
        //期望，新值，预期版本，新版本
        atomicStampedReference.compareAndSet(book, Mysql, stamp,stamp+1);
    }

    /**
     * 多线程ABA问题实战
     *
     */
    //不使用
    static void m2(){
        AtomicInteger atomicInteger = new AtomicInteger(100);
        //线程A
        new Thread(()->{
            atomicInteger.compareAndSet(100,101);
            System.out.println("哈哈，我修改了");
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicInteger.compareAndSet(101,100);
        },"A").start();

        //线程B
        new Thread(()->{
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicInteger.compareAndSet(100, 102);
            System.out.println("值为："+atomicInteger.get());
        },"B").start();
    }

    //使用
    static void m3(){
        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<Integer>(100,1);
        new Thread(()->{
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            integerAtomicStampedReference.compareAndSet(100,101,
                    integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp()+1);
            System.out.println("哈哈哈，我修改了");
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            integerAtomicStampedReference.compareAndSet(101,100,
                    integerAtomicStampedReference.getStamp(),integerAtomicStampedReference.getStamp()+1);

        },"A").start();

        new Thread(()->{
            int stamp = integerAtomicStampedReference.getStamp();
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = integerAtomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(b+" 值"+integerAtomicStampedReference.getReference()+" 版本："+integerAtomicStampedReference.getStamp());
        },"B").start();

    }
}

class Book{
    private Integer id;
    private String Name;
}
