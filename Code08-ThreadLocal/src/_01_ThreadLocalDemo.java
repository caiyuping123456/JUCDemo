import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/21 21:39
 * @description: 业务
 */
public class _01_ThreadLocalDemo {
    public static void main(String[] args) {
        Process process = new Process();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                int size = new Random().nextInt(5)+1;
                for (int j = 1; j < size; j++) {
                    process.setSaleVolume();
                }
                System.out.println(Thread.currentThread().getName()+"号,卖出："+process.saleVolume.get());
            },""+i).start();
        }
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("总共卖出："+process.saleVolume.get());
    }

    /**
     * ThreadLocal提供线程局部变量，这些变量同正常的变量不同，因为每一个线程在访问ThreadLocal实例的时候（通过set和get方法）
     * 都有自己的，独立初始化的变量副本。
     * ThreadLocal实例通常是类中的私有静态字段，使用它的目的是希望将状态同线程进行关联起来。
     *
     * 出现的意义：不用加锁了，保证线程安全。
     *
     *      总结：每个Thread内部有一个实例副本，只能当前自己使用，其他线程不能进行使用。
     *
     *
     */
}

//资源类
class Process{
    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(()->0);

    public void setSaleVolume(){
        saleVolume.set(1+saleVolume.get());
    }
}
