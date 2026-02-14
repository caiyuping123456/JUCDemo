/**
 * @author caiyuping
 * @date 2026/2/20 16:06
 * @description: 业务
 */
public class _02_LongAdderDemo {

    //LongAdder源码解析
    /**
     * LongAdder是高性能的(地数据可以使用传统原子类，)
     *
     *      LongAdder继承了Striped64
     *      Striped64内部有一个Cell和一个base，
     *
     */

    /**
     * 为什么LongAdder会这么快？
     *      由原始的CAS的整数为零==>分散热点===>最后进行求和
     *      基本思路就是分散了点，将value值分散到一个Cell数组中，不同线程会命中不同的数据槽，各个线程只对自己的槽中的值进行CAS操作，这也热点就进行了分散。冲突就小很多。
     *      最后由sum进行所有的value和base累加做返回值，核心是将之前的value分散到多个value值中。
     *      从而降级热点。单线程使用base,多线程使用cell数组。
     *      （内部有一个base变量和一个cell数组，全部求和）
     */

    /**
     * 源码解读：
     *      进入的线程根据哈希算法进行计算自己所在的Cell数据槽
     *
     *      深入了解累加方法：
     *
     *      深入longAccumulate();
     */
}

























