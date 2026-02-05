import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author caiyuping
 * @date 2026/2/5 15:16
 * @description: Lambda表达式+Stream流式调用+Chain链式调用加Java8新特性
 */
public class _06_CompletableFutureChainUse {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        a();
    }

    /**
     * java8新特性
     *  1. Runnable：没有参数，没有返回值======>方法：run
     *  2. Function：接受一个参数，有返回值======>方法：apply
     *  3. Consumer：接受一个参数，没有返回值  =====>BiConsumer：接受两个参数，没有返回值======>方法：accept
     *  4. Supplier：没有参数，有返回值=========>方法：get
     */
    static void a() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return "你好";
        });

        //get和join式差不多的，join会在编译时抛出是否有检测异常
        //System.out.println(stringCompletableFuture.get());
        System.out.println(stringCompletableFuture.join());

    }
}

//这里可以使用lombok的Accessor进行chain式调用
record Student(String name,Integer age,String major){}
