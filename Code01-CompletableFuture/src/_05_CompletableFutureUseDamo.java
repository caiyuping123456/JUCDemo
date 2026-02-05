import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/4 23:00
 * @description: 异步任务回调
 */
public class _05_CompletableFutureUseDamo {
    public static void main(String[] args) {
//        a();
        b();
    }

    /**
     * 这里看到，如何只用默认的线程池也就是ForkJoinPool，这个异步任务是守护线程
     * 在主线程退出时，会自动关闭
     * 这里可以延迟主线程退出时机或者传入自己定义的线程池
     */
    static void a(){
        CompletableFuture.supplyAsync(() -> {
            //这里直接调用
            System.out.println("异步任务执行========");
            int result = Math.round(10);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("随机数为："+result);
            return result;
        }).whenComplete((var,e) -> {
            System.out.println("================打印阶段");
            System.out.println("打印的结果为："+var);
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println("异常结果为："+e);
            return null;
        });

        System.out.println("主线程执行其他任务");
    }

    /**
     * 可以看出，结果响应可，没有进行线程睡眠
     */
    static void b(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try{
            CompletableFuture.supplyAsync(() -> {
                //这里直接调用
                System.out.println("异步任务执行========");
                int result = Math.round(10);
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("随机数为："+result);
                return result;
            },executorService).whenComplete((var,e) -> {
                System.out.println("================打印阶段");
                System.out.println("打印的结果为："+var);
            }).exceptionally(e->{
                e.printStackTrace();
                System.out.println("异常结果为："+e);
                return null;
            });

            System.out.println("主线程执行其他任务");
        } finally {
            executorService.shutdown();
        }

    }
}
