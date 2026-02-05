import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/4 21:38
 * @description: 使用FutureTask配合线程池进行任务执行
 */
public class _02_FutureTaskUseThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 这里表明，FutureTask的get方法会阻塞调用的线程去等待任务完成的结果
         */
        one();
    }
    static void one() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        FutureTask<String> task1 = new FutureTask<>(() -> {
            System.out.println("执行任务1=========》come on");
            sleep(5000);
            return "线程1执行完毕";
        });
        FutureTask<String> task2 = new FutureTask<>(() -> {
            System.out.println("执行任务2=========》come on");
            sleep(3000);
            return "线程2执行完毕";
        });
        FutureTask<String> task3 = new FutureTask<>(() -> {
            System.out.println("执行任务3=========》come on");
            sleep(2000);
            return "线程3执行完毕";
        });
        Thread thread = new Thread(task1,"t1");
        executorService.submit(task2);
        executorService.submit(task3);
        thread.start();

        //关闭线程池
        executorService.shutdown();

        System.out.println(task1.get());
        System.out.println(task2.get());
        System.out.println(task3.get());
        long end = System.currentTimeMillis();
        System.out.println("用时："+(end-start));
    }
}
