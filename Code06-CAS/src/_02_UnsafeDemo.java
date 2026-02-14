import java.util.concurrent.atomic.AtomicReference;

/**
 * @author caiyuping
 * @date 2026/2/14 14:53
 * @description: 业务
 */
public class _02_UnsafeDemo {
    /**
     * Unsafe是CAS的核心类，由于Java方法语法直接访问底层数据库，需要通过本地的native方法进行访问，Unsafe相当于一个后门
     * 这个类可以操作特定内存的数据，Unsafe在sun.misc包下，器内部方法操作可以像C的指针一样操作内存，因为Java中CAS操作类的执行，依赖于
     * Unsafe类的方法
     *
     * Unsafe类中的方法都是native进行修饰的，就是说Unsafe中的方法直接调用操作系统最底层的任务
     */

    /**
     * 原子包装类
     *
     */
    static void m1(){
        AtomicReference<Student> studentAtomicReference = new AtomicReference<>();
        Student student = new Student();

        studentAtomicReference.set(student);
    }
}

class Student{
    private String Name;
    private Integer age;
}
