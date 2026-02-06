import java.util.concurrent.locks.ReentrantLock;

/**
 * @author caiyuping
 * @date 2026/2/7 13:12
 * @description: 可从重入锁
 */
public class _06_ReentrantLockDec {
    /**
     * 可congruent锁是表示，一个线程在外部代码块获取到锁后，同样也可以在外部再次获取锁（不发生死锁），可重入锁也被叫做递归锁
     * 本质上可以避免死锁发发生。
     */

    public static void main(String[] args) {
        //m1();
        //_06_ReentrantLockDec reentrantLockDec = new _06_ReentrantLockDec();
        //reentrantLockDec.m2();
        m5();
    }

    /**
     * 可重入锁synchronized演示
     * 注意：synchronized这个是隐式锁
     *
     * 下面这个代码可以看出，表示synchronized不发生死锁
     */
    static void m1(){
        Object o = new Object();
        new Thread(()->{
            synchronized (o){//外部获取锁
                System.out.println("==========外部代码调用");
                synchronized (o){//内部获取锁
                    System.out.println("=============中层代码调用");
                    synchronized (o){
                        System.out.println("==========内部代码调用");
                    }
                }
            }
        }).start();
    }

    /**
     * 使用递归调用查看是否会发生死锁
     * 可以看出，这个是同一个对象，所以不发生死锁
     */
    synchronized void m2(){
        System.out.println(Thread.currentThread().getName()+" "+"==========come in");
        m3();
        System.out.println(Thread.currentThread().getName()+" "+"==========end");
    }
    synchronized void m3(){
        System.out.println(Thread.currentThread().getName()+" "+"==========come in");
        m4();
    }
    synchronized void m4(){
        System.out.println(Thread.currentThread().getName()+" "+"==========come in");
    }

    /**
     * synchronized可重入的原理：
     *      1. 本质就是moniter监视器会查看当前线程是不是锁持有对象，如果是就进行1计数器加一。，否之就需要等待。
     *      2. 执行enter时进行计数器加一，执行exit时进行计数器减一，直到计数器变为0，锁释放。
     */


    /**
     * lock锁验证
     * lock是一个显式锁，需要自己手动释放
     * 就是Lock和unlock需要成对出现，不然会导致死锁。
     */
    static void m5(){
        ReentrantLock lock = new ReentrantLock();//锁对象
        new Thread(()->{
            lock.lock();//获取锁
            try{
                System.out.println("=======外部代码进入");
                lock.lock();//获取锁
                try{
                    System.out.println("=======中层代码进入");
                    lock.lock();//获取锁
                    try{
                        System.out.println("=======内部代码进入");
                    }finally {
                        lock.unlock();//释放锁
                    }
                }finally {
                    lock.unlock();//释放锁
                }
            }finally {
                lock.unlock();//释放锁
            }
        }).start();
    }
}
