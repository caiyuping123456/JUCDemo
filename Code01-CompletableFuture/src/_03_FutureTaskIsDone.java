import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/4 21:53
 * @description: FutureTask的轮询方法，轮询CPU空转
 */
public class _03_FutureTaskIsDone {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        /**
         * 这里可以看出，FutureTask获取任务就是通过阻塞或者轮询获取的
         */
        one();
    }

    /**
     * 测试get的限时功能
     */
    static void one() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            System.out.println("===============come on");
            System.out.println("睡眠5秒");
            sleep(5000);
            return "你好1";
        });
        Thread thread = new Thread(stringFutureTask,"t1");
        thread.start();
        //3秒结束
        //System.out.println(stringFutureTask.get(3, TimeUnit.SECONDS));

        //下面使用isDone进行任务轮询
        while(true){
            if(stringFutureTask.isDone()){
                System.out.println("任务执行完成："+stringFutureTask.get());
                break;
            }else {
                //500毫秒轮询一次
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("任务还在执行=======");
            }
        }
    }
}
