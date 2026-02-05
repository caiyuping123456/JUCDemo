import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/4 22:30
 * @description: CompletableFuture的4个静态方法
 */
public class _04_ComplatableFutureStaticMethod {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //Method_1_RunAsync();
        //Method_2_RunAsyncPool();
        //Method_3_RunAsyncPool();
        Method_4_RunAsyncPool();
    }

    /**
     * 可以看出这个是没有返回值的方法，同时使用的线程池是默认的ForkJoinPool
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void Method_1_RunAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("现在执行的是无返回值，不传入线程池的异步编排任务类");
            System.out.println("线程池的名字"+currentThread().getName());
            //睡眠5秒
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 主线程获取
        System.out.println(voidCompletableFuture.get());
    }

    /**
     * 可以看出这个是没有返回值的方法，同时使用的线程池是自己传入的线程池
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void Method_2_RunAsyncPool() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("现在执行的是无返回值，不传入线程池的异步编排任务类");
            System.out.println("线程池的名字"+currentThread().getName());
            //睡眠5秒
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },executorService);

        System.out.println("任务返回"+voidCompletableFuture.get());
        executorService.shutdown();
    }

    /**
     * 可以看出这个是没有返回值的方法，同时使用的线程池是默认的ForkJoinPool
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void Method_3_RunAsyncPool() throws ExecutionException, InterruptedException {
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("现在执行的是无返回值，不传入线程池的异步编排任务类");
            System.out.println("线程池的名字"+currentThread().getName());
            //睡眠5秒
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "你好 supplyAsync";
        });

        // 主线程获取
        System.out.println(voidCompletableFuture.get());
    }

    /**
     * 可以看出这个是有返回值的方法，同时使用的线程池是自己传入的线程池
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void Method_4_RunAsyncPool() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("现在执行的是无返回值，不传入线程池的异步编排任务类");
            System.out.println("线程池的名字"+currentThread().getName());
            //睡眠5秒
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "你好 supplyAsync";
        },executorService);

        System.out.println("任务返回"+voidCompletableFuture.get());
        executorService.shutdown();
    }
}
