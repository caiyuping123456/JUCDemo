import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.*;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/5 16:05
 * @description: 业务
 */
public class _08_CompletableFutureMethod {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //a();
        //b();
        //c();
        //c_d();
        //d();
        e();
    }

    /**
     * 获得结果和触发计算
     */
    static void a() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                //计算时间
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "abc";
        });
        //等待时间
        sleep(5000);

        //阻塞等待
        //System.out.println(stringCompletableFuture.get());
        //只等待两秒（过时不候）
        //System.out.println(stringCompletableFuture.get(2, TimeUnit.SECONDS));
        //join会再运行是抛出异常
        //System.out.println(stringCompletableFuture.join());
        //如果抛出了异常，就返回xxx（备胎值）
        //System.out.println(stringCompletableFuture.getNow("xxx"));
        //直接打断，同时使用括号中的值（为true）
        System.out.println(stringCompletableFuture.complete("completable")+"  "+stringCompletableFuture.join());

    }

    /**
     * 对计算结果进行处理
     */
    static void b(){
        //测试thenApply，这个是再没有异常时就会进行下一步，有的话就会抛出
//        CompletableFuture<Integer> exceptionally = CompletableFuture.supplyAsync(() -> {
//            //第一步
//        System.out.println("1111");
//            return 1;
//        }).thenApply(f -> {
//            //第二步
//            //这里模拟异常
//            /**
//             * 可以看出，有异常，直接抛出，不会执行下面的了
//             */
//            int i = 10/0;
//        System.out.println("2222");
//            return f + 2;
//        }).thenApply(f -> {
//            //第三步
//        System.out.println("3333");
//            return f + 3;
//        }).whenComplete((f, e) -> {
//            if (e == null) {
//                System.out.println(f);
//            }
//        }).exceptionally(e -> {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            return null;
//        });

        //下面开始测试handle
        // handle是和thenApply差不多，就是在异常处理这里会进行跳过，执行下面的
        CompletableFuture<Integer> exceptionally = CompletableFuture.supplyAsync(() -> {
            //第一步
            System.out.println("1111");
            return 1;
        }).handle((f,e)-> {
            //第二步
            //这里模拟异常
            /**
             * 可以看出，有异常，直接抛出，不会执行下面的了
             */
            int i = 10/0;
            System.out.println("2222");
            return f + 2;
        }).handle((f,e) -> {
            //第三步
            System.out.println("3333");
            return f + 3;
        }).whenComplete((f, e) -> {
            if (e == null) {
                System.out.println(f);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });
        System.out.println(exceptionally.join());
    }


    /**
     * 对计算结果进行消费
     */
    static void c(){
//       CompletableFuture.supplyAsync(() -> {
//            //第一步
//            System.out.println("1111");
//            return 1;
//        }).handle((f,e)-> {
//            //第二步
//            //这里模拟异常
//            /**
//             * 可以看出，有异常，直接抛出，不会执行下面的了
//             */
////            int i = 10/0;
//            System.out.println("2222");
//            return f + 2;
//        }).handle((f,e) -> {
//            //第三步
//            System.out.println("3333");
//            return f + 3;
//
//            //这个是消费性函数接口
//        }).thenAccept(f->{
//            System.out.println(f);
//        });

        //thenRun========>任务A执行完执行B，同时B不需要A的结果
        System.out.println(CompletableFuture.supplyAsync(() -> "resulteA").thenRun(() -> {}).join());
       //thenAccept========================>任务A执行完执行B,同时B需要A的结果，B没有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resulteA").thenAccept(f -> System.out.println(f+"哈哈哈哈")).join());
       //thenApply========>任务A执行完执行B,同时B需要A的结果，B有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resulteA").thenApply(f -> f+"哈哈哈哈").join());


    }

    /**
     * 补充
     * thenRun和thenRunAsync的区别
     * 可以看出，在不使用自定义线程池的时候，默认是使用ForkJoinPool线程池
     * 如果使用了自定义线程池，如果第一个是thenRun，那后面的都是使用的是同一个线程池
     * 如果第一个为thenRunAsync，只有这一个thenRunAsync使用的自定义线程池，后面的使用都是默认线程池
     *
     * 补充，如果第一个任务执行太快，后面的任务可能会使用mian线程。
     */
    static void c_d(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try{
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    sleep(60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("1号任务：" + currentThread().getName());
                return "abcd";
            },executorService).thenRunAsync(() -> {
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("2号任务：" + currentThread().getName());
            }).thenRun(() -> {
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("3号任务：" + currentThread().getName());
            }).thenRun(() -> {
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("4号任务：" + currentThread().getName());
            });

            System.out.println(voidCompletableFuture.get(2, TimeUnit.SECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }

    }

    /**
     * 对计算速度进行选用
     * 谁快用谁
     * applyToEither
     */
    static void d(){
        CompletableFuture<String> A = CompletableFuture.supplyAsync(() -> {
            System.out.println("A come in");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "A";
        });
        CompletableFuture<String> B = CompletableFuture.supplyAsync(() -> {
            System.out.println("B come in");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "B";
        });

        //这个就是进行快慢比较的
        CompletableFuture<String> stringCompletableFuture = A.applyToEither(B, f -> {
            return f + "win";
        });

        System.out.println(stringCompletableFuture.join());
    }

    /**
     * 对计算结果进行合并
     * 两个任务完成，通过合并进行放回
     */
    static void e(){
        CompletableFuture<Integer> A = CompletableFuture.supplyAsync(() -> {
            System.out.println(currentThread().getName() + "===========启动");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        });

        CompletableFuture<Integer> B = CompletableFuture.supplyAsync(() -> {
            System.out.println(currentThread().getName() + "===========启动");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        });

        CompletableFuture<Integer> integerCompletableFuture = A.thenCombine(B, (x, y) -> {
            System.out.println("两个进行合并");
            return x + y;
        });

        System.out.println(integerCompletableFuture.join());
    }
}
