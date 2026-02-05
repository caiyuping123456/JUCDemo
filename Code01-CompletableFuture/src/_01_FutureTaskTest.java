import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/4 21:32
 * @description: 测试task
 */
public class _01_FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            System.out.println("正在调用FutureTask进行执行任务");
            sleep(5000);
            System.out.println("执行完毕");
            return "你好";
        });

        Thread thread = new Thread(stringFutureTask,"str");
        thread.start();
        System.out.println("这是主线程");
        System.out.println("哈哈哈哈");
        //进行FutureTask的执行结果调用
        System.out.println(stringFutureTask.get());

    }
}
