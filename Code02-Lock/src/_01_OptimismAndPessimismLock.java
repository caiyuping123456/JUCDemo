/**
 * @author caiyuping
 * @date 2026/2/6 15:16
 * @description: 从乐观锁和悲观锁进行入门
 */
public class _01_OptimismAndPessimismLock {
    public static void main(String[] args) {

    }

    /**
     * 乐观锁
     * 认为自己在使用数据时候，不会有别的线程修改数据，所以不会添加锁
     * Java是通过无所编程进行实现的，只有在跟新数据的时候才会去判断之前没有别的线程更新了这个数据
     * 判断规则：
     *  1. 使用版本号进行判断
     *  2. 采用CAS算法进行自旋实现（比较替换）
     *
     * 一般在度操作用的多
     */
    static void OptimismLock(){
        //这里可以使用一个原子类进行乐观加锁
    }

    /**
     * 悲观锁
     * 认为自己在使用数据时候，会有别的线程修改数据，所以会添加锁
     * 一般会通过Synchronized进行加锁
     *
     * 一般在写操作用的多
     */
    static void PessimismLock(){

        //这里使用synchronized或者lock进行悲观加锁
    }
}
