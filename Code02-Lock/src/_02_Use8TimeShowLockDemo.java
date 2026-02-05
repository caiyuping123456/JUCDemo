import static java.lang.Thread.sleep;

/**
 * @author caiyuping
 * @date 2026/2/6 15:27
 * @description: 使用8种案例演示锁
 *
 * synchronized分为对象锁和类锁，
 * 只有一个线程能进入synchronized中，
 *
 * 加了static进行修饰的synchronized，就是类锁
 *
 * 对于synchronized锁，有三种同步方法：
 *  1. 普通同步方法：当前对象，就是this
 *  2. 静态同步方法：就是这个类，phone.class
 *  3. 同步方法块：括号中的对象
 *
 *  阻塞只存在于同一个锁
 */
public class _02_Use8TimeShowLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone.sms();
        },"B").start();
    }

    /**
     * 标准访问，有两个线程，请问先打印邮件还是SMS?
     * 先邮件，再SMS
     */
    static void Demo_01_() throws InterruptedException {
        Phone phone = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone.sms();
        },"B").start();
    }

    /**
     * email中暂停3秒，请问先打印邮件还是SMS?
     * 还是先邮件，再SMS
     */
    static void Demo_02_() throws InterruptedException {
        Phone phone = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone.sms();
        },"B").start();
    }

    /**
     * 资源类中添加一个普通的Hello方法，请问先打印邮件还是SMS?
     * 先hello，再邮件
     */
    static void Demo_03_() throws InterruptedException {
        Phone phone = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone.hello();
        },"B").start();
    }

    /**
     * 两部手机，请问先打印邮件还是SMS?
     * 先sms，再邮件
     */
    static void Demo_04_() throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone2.sms();
        },"B").start();
    }

    /**
     * 两个静态方法,1部手机，请问先打印邮件还是SMS?
     * 先sms，再邮件
     */
    static void Demo_05_() throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        //A线程
        new Thread(()->{
            try {
                phone.email();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //保证A线程先启动
        sleep(200);

        //B线程
        new Thread(()->{
            phone2.sms();
        },"B").start();
    }
}

//资源类
class Phone{

    //发邮件
    public static synchronized void email() throws InterruptedException {
        sleep(3000);
        System.out.println("发送邮件");
    }

    //发SMS
    public synchronized void sms(){
        System.out.println("发送SMS");
    }

    //普通的hello
    public void hello(){
        System.out.println("Hello");
    }

}
