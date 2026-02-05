/**
 * @author caiyuping
 * @date 2026/2/6 16:12
 * @description: Synchronized有三种应用方式
 *  1. 执行一个Synchronized实例方法
 *  2. 使用Synchronized同步代码块
 *  3. 使用静态的Synchronized方法
 */
public class _03_Synchronized3TimeMethod {
    public static void main(String[] args) {

    }

    /**
     * 这个是静态代码块
     * 通过使用monitorenter和monitorexit指令实现（成对）
     */
    static void aVoid(){
        PhoneDemo phone = new PhoneDemo();
        synchronized (phone){
            System.out.println("m1");
        }
    }


}

class PhoneDemo{

    Object object = new Object();

    /**
     * synchronized代码块
     */
    public void m1(){
        synchronized (object){
            System.out.println("m1");
        }
    }


    /**
     * 字节码底层是通过ACC_STATIC进行判断是不是静态同步方法
     */

    /**
     * 普通的synchronized同步方法
     */
    public synchronized void m2(){
        System.out.println("m2");
    }

    /**
     * 静态的synchronized同步方法
     */
    public synchronized void m3(){
        System.out.println("m3");
    }
}
