/**
 * @author caiyuping
 * @date 2026/2/13 0:39
 * @description: 业务
 */
public class _03_JMMOfReadWrite {
    /**
     * JMM中多线程对变量的读写过程
     *      线程中有个自己的工作线程，对变量的操作是在自己的工作区间（变量是主内存的副本），不直接操作主线程（读写）
     *      修改好了后刷回主内存，同时通知其他线程，
     */
}
