import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/7 13:35
 * @description: 死锁
 */
public class _07_DeadlockDemo {
    /**
     * 死锁是什么？
     * 死锁式表示两个及两个线程以上在执行过程中，因为资源争夺导致相互等待的现象就是死锁。
     */
    public static void main(String[] args) {
        m1();
    }
    /**
     * 死锁的case
     * 死锁产生的原因：
     *      1. 系统资源不足
     *      2. 进程推进的顺序不合适
     *      3. 资源分配不当
     */
    static void m1(){
        Object A = new Object();
        Object B = new Object();

        new Thread(()->{
            //先获取A
            synchronized (A){
                //获取B
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (B){
                    System.out.println("A结束");
                }
            }
        },"A").start();

        new Thread(()->{
            //先获取B
            synchronized (B){
                //获取A
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (A){
                    System.out.println("B结束");
                }
            }
        },"B").start();
    }

    /**
     * 死锁排查：
     *      1. 使用JDK的原生命令：jps -l(查看执行的程序) + jstack 进程编号
     *      2. 使用图形化：jconsole
     */
}
