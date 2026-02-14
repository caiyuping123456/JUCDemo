import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

import static java.lang.Thread.sleep;
import static java.lang.Thread.yield;

/**
 * @author caiyuping
 * @date 2026/2/18 15:41
 * @description: 业务
 */
public class _01_atomicDemo {
    public static void main(String[] args) throws InterruptedException {
        //m1();
        //m2();
        //m3();
        //m4();
        m5();
    }
    /**
     * 原子类入门和分类说明
     *  五大分类：
     *          1. 基本类型原子类：AtomicInteger,AtomicBoolean,AtomicLong
     *          2. 数组类型的原子类
     *          3. 引用类型的原子类
     *          4. 对象的属性修改原子类
     *          5. 原子类型增强原子类(jdk8新增的)
     */

    /**
     * 基本类型原子类
     * 在多线程并发时，需要主线程等待子线程执行完成。
     * CountDownLatch可以解决
     */
    static void m1(){
        Text text = new Text();
        //这个时线程估算的，只有当里面的线程数为0时，才会接着执行awite下面的
        CountDownLatch countDownLatch = new CountDownLatch(50);
        for(int i=0;i<50;i++){
            new Thread(()->{
                try {
                    text.m1();
                }finally {
                    countDownLatch.countDown();
                }
            },""+i).start();
        }
//        try {
//            sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(text.atomicInteger.get());
    }

    /**
     * 数组类型的原子类:单个变为多个的
     */
    static void m2(){
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        System.out.println("初始化");
        for(int i =0 ;i <5;i++){
            System.out.println(atomicIntegerArray.get(i));
        }

        System.out.println("修改");
        int temp = 0;
        temp = atomicIntegerArray.getAndSet(temp, 11223);
        System.out.println(temp+" "+atomicIntegerArray.get(0));

        System.out.println("累加");
        temp = atomicIntegerArray.getAndIncrement(0);
        System.out.println(temp+" "+atomicIntegerArray.get(0));
    }

    /**
     * 引用类型原子类
     * AtomicReference
     * AtomicStampedReference,version版本号，+1,,修改多少次
     * AtomicMarkableReference,一次，false|true，，是否修改过
     */
    static void m3(){
        //AtomicStampedReference
        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<>(1,1);
        System.out.println(integerAtomicStampedReference.getReference());
        System.out.println(integerAtomicStampedReference.getStamp());
        System.out.println("修改");
        integerAtomicStampedReference.set(100, 2);
        System.out.println(integerAtomicStampedReference.getReference());
        System.out.println(integerAtomicStampedReference.getStamp());

        //AtomicMarkableReference
        AtomicMarkableReference<Integer> integerAtomicMarkableReference = new AtomicMarkableReference<>(2, false);
        System.out.println(integerAtomicMarkableReference.getReference());
        System.out.println(integerAtomicMarkableReference.isMarked());

        integerAtomicMarkableReference.compareAndSet(2,1000,false,true);
        System.out.println(integerAtomicMarkableReference.getReference());
        System.out.println(integerAtomicMarkableReference.isMarked());

    }

    /**
     * 对象的属性修改原子类
     * AtomicIntegerFieldUpdater....
     *
     * 以一种线程安全的方式去操作非线程安全对象内部的某些字段
     * 使用要求：跟新的对象必须使用public volatile进行修改
     *
     *       10个线程，每个线程转账一千，使用AtomicIntegerFieldUpdater进行
     */
    static void m4() throws InterruptedException {
        Bank bank = new Bank();
        //10个线程
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(()->{
                 try{
                     for(int j=0;j<1000;j++){
                         bank.tranMoney(bank);
                    }
                }finally {
                    countDownLatch.countDown();
                }
            },""+i).start();
        }
        countDownLatch.await();

        System.out.println(bank.money);
    }

    /**
     * 原子类型增强原子类jdk新增的，更加牛逼
     * 可以减少乐观锁的重试次数
     *
     * 这个适合使用在高并发环境下使用
     *      LongAdder只能用来计算加法，且从零开始
     *      LongAccumulator可以提供自定义的函数操作
     */
    static void m5(){
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(4);
        System.out.println(longAccumulator.get());
    }

    //热点商品点赞计数器
    //50个线程，每个线程100万次
    static void m6(){
        //开始时间
        long start = System.currentTimeMillis();

        //结束时间
        long end = System.currentTimeMillis();

        System.out.println("用时 "+(end-start));

    }


}

//资源类
class ClickNumber{
    int number = 0;
    //传统的
    public synchronized void clickBySynchronized(){
        number++;
    }

    //使用AtomicLong
    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong(){
        atomicLong.getAndIncrement();
    }

    //使用后LongAdder进行
    LongAdder longAdder = new LongAdder();
    public void clickByLongAdder(){
        longAdder.increment();
    }

    //使用clickByLongAccumulator可以提供自定义的函数操作
    LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y, 0);
    public void clickByLongAccumulator(){
        longAccumulator.accumulate(1);
    }
}

class Bank{
    String bankName;

    //100可以进行相加
    //不使用synchronized
    public void add(){
        money++;
    }

    //下面使用使用AtomicIntegerFieldUpdater进行
    public volatile int money = 0;
    //静态方法的更新器
    AtomicIntegerFieldUpdater<Bank> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Bank.class,"money");
    //不加锁实现
    public void tranMoney(Bank bank){
        fieldUpdater.getAndIncrement(bank);
    }

}

class Text{
    AtomicInteger atomicInteger = new AtomicInteger();

    void m1(){
        atomicInteger.getAndIncrement();
    }
}
