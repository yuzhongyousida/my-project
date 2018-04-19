package jdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: wangteng
 * @description: jdk Object类实验
 * @date:2018/3/8
 */
public class ObjectTest {

    public static void main(String[] args) {
        try {
//            getAnnotationTest();
            equalsTest();


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * <A extends Annotation> A getAnnotation(Class<A> annotationClass) 方法实验
     * 方法返回该元素的指定类型的注解，如果有，则返回这样的注释，否则返回null
     */
    private static void getAnnotationTest() throws NoSuchMethodException, NoSuchFieldException {
        User user = new User();

        // 获取类上的注解
        Class<?> userClazz = user.getClass();
        Service serviceAnnotation = userClazz.getAnnotation(Service.class);
        if(serviceAnnotation==null){
            System.out.println("user class has no service annotation");
        }

        // 获取方法上的注解
        Method method = userClazz.getMethod("getId");
        Override overrideAnnotation = method.getAnnotation(Override.class);
        if(overrideAnnotation==null){
            System.out.println("jdk.User.getId() method has no override annotation");
        }

        // 获取属性上的注解
        Field idField = userClazz.getDeclaredField("id");
        Autowired autowiredAnnotation = idField.getAnnotation(Autowired.class);
        if(autowiredAnnotation==null){
            System.out.println("jdk.User.id field has no autowired annotation");
        }

    }


    /**
     * equals方法实验
     * equals方法的通用约定：
     * 自反性：对于任何非null的引用值x，x.equals(x)必须返回true。
     * 对称性：对于任何非null的引用值x和y，当且仅当y.equals(x)返回true时，x.equals(y)必须返回true。
     * 传递性：对于任何非null的引用值x、y、z，如果x.equals(y)返回true，并且y.equals(z)返回true，那么x.equals(z)也必须返回ture。
     * 一致性：对于任何非null的引用值x和y，只要equals的比较操作在对象中所用的信息没有被修改，多次调用x.equals(y)就会一致的返回ture，或者一致的返回false。
     * 非空性：对于任何非null的引用值x，x.equals(null)必须返回false。
     * 当我们在类继承尤其要注意实现的equals方法是否满足约定
     * 当我们覆盖了equals方法时，一定不能忘记覆盖hashCode方法
     *
     */
    private static void equalsTest(){
        User user1 = new User();
        user1.setId(1);
        user1.setName("wangteng");

        User user2 = new User();
        user2.setId(1);
        user2.setName("wangteng");

        System.out.println("user1 equals user2 result: " + user1.equals(user2));
        System.out.println("user1.hashCode equals user2.hashCode result: " + user1.equals(user2));

    }




}
