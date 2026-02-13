import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/13 17:09
 * @description: 业务
 */
public class _03_volatileCase {

    public static void main(String[] args) {
        m1();
    }
    /**
     * 可见性案例：read(读取)==>load（加载）===>use（使用）===>assgin（赋值）===>store（存储）===>write（写入）====>lock（加锁）===>unlock（解锁）
     *      没有添加volatile,没有可见性
     *      加了volatile表示有可见性
     */
    static volatile boolean flag = true;
    static void m1(){
        Thread A = new Thread(() -> {
            System.out.println("come======>in");
            while(flag){}
            System.out.println("flag设置为可见性");
        }, "A");
        A.start();

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            flag = false;
            System.out.println("修改成功");
        },"B").start();

    }

    /**
     * 没有原子性：
     *
     */

    /**
     * 指令禁止重排
     */
}
