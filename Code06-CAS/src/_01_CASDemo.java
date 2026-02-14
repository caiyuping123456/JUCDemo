import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caiyuping
 * @date 2026/2/14 14:31
 * @description: 业务
 */
public class _01_CASDemo {
    /**
     * 对于i++这种，如果没有使用CAS，多线程系统不会使用原子类保证线程安全
     * 使用CAS就会使用原子类进行保护线程安全
     */
    AtomicInteger atomicInteger = new AtomicInteger();
    public int getAtomicInteger(){
        return atomicInteger.get();
    }
    public void setAtomicInteger(){
        atomicInteger.getAndIncrement();
    }

    /**
     * CAS有点像乐观锁
     * CAS是对比交换的缩写，是实现并发算法时常使用的一种技术
     * 它包含三个操作数——内存位置，预期原值及更新值。
     *      执行CAS时，将内存位置的值同预期原值进行比较，
     *      如果相匹配，那么处理器会自动将该位置值更新为新值。
     *      如何不匹配，处理器不会做任何操作，多个线程同时执行CAS操作只有一个会成功
     */

    /**
     * CAS是JDK提供的非阻塞原子性操作，它通过硬件保证了比较-更新的原子性
     * 它是非阻塞的且自身具有原子性，也就是说这个东西效率高且通过硬件保证，说明这个玩意更加可靠
     * CAS是CPU的一种原子指令，UnSafe提供的CAS方法，底层就是CPU的cmpxchg指令
     */
}
