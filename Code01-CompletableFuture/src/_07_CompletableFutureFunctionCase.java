import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/5 15:30
 * @description: 业务
 */
public class _07_CompletableFutureFunctionCase {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> mysql = getPrice_02(list, "mysql");
        for (String name : mysql){
            System.out.println(name);
        }
        long end = System.currentTimeMillis();
        System.out.println("花费时间："+(end-start));
    }

    //电商平台
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    /**
     * 一家一家查（one by one） ,同款产品在各个平台的售价
     * 这个是普通的流式调用
     * @param list
     * @param productName
     * @return
     */
    static List<String> getPrice_01(List<NetMall> list, String productName){
        //利用Stream流进行链式调用，放回List
        List<String> collect = list.stream().map(netMall -> {
            return String.format(productName + "in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName));
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 使用CompletableFuture配合流式进行调用
     * @param list
     * @param productName
     * @return
     */
    static List<String> getPrice_02(List<NetMall> list, String productName){
        //利用Stream流进行链式调用，放回List
        //这里是通过CompletableFuture进行异步任务编排
        List<String> collect = list.stream().map(netMall -> CompletableFuture.supplyAsync(() ->
                String.format(productName + "in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName))
            )
        ).collect(Collectors.toList())
                //再进行结果合并
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
        return collect;
    }
}

class NetMall{
    private String netMallName;
    public double calcPrice(String productName){
        //模拟查找时间为1秒
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //生成一个随机的金额
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }

    public String getNetMallName() {
        return netMallName;
    }

    public void setNetMallName(String netMallName) {
        this.netMallName = netMallName;
    }

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }
}
