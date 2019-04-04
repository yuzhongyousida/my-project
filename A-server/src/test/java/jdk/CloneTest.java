package jdk;

import com.myself.model.Identity;
import com.myself.model.User;
import com.myself.util.CloneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/19
 */
public class CloneTest {

    public static void main(String[] args) {
        try {
            test1();

            System.out.println("======================");

            test2();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    private static void test1() throws CloneNotSupportedException {
        long start = System.currentTimeMillis();

        // 封装user1
        Identity identity = new Identity();
        identity.setAge(10);
        identity.setIdNo("00000000000000");

        List<String> schoolNames = new ArrayList<>();
        schoolNames.add("家里蹲大学");
        identity.setSchoolingNames(schoolNames);

        User user1 = new User();
        user1.setUserName("wangteng");
        user1.setPassword("123456");
        user1.setIdentity(identity);

        // 利用Object的原生clone()方法clone一个对象user2
        User user2 = (User) user1.clone();

        // 比较user1和user2的引用
        boolean result1 = user1==user2;
        System.out.println("user1 == user2 : " + result1);

        // 比较user1和user2的简单类型的属性password属性（String类型，比较特殊点）
        boolean result2 = user1.getPassword()==user2.getPassword();
        System.out.println("user1.getPassword() == user2.getPassword() : " + result2);

        // 比较user1和user2的复杂类型的属性identity属性
        boolean result3 = user1.getIdentity()==user2.getIdentity();
        System.out.println("user1.getIdentity() == user2.getIdentity() : " + result3);

        System.out.println("花费时间：" + (System.currentTimeMillis()-start) + "ms");
    }


    private static void test2() throws CloneNotSupportedException {
        long start = System.currentTimeMillis();

        // 封装user1
        Identity identity = new Identity();
        identity.setAge(10);
        identity.setIdNo("00000000000000");

        List<String> schoolNames = new ArrayList<>();
        schoolNames.add("家里蹲大学");
        identity.setSchoolingNames(schoolNames);

        User user1 = new User();
        user1.setUserName("wangteng");
        user1.setPassword("123456");
        user1.setIdentity(identity);

        // 利用对象的序列化、反序列化达到深度clone
        User user2 = CloneUtil.clone(user1);

        // 比较user1和user2的引用
        boolean result1 = user1==user2;
        System.out.println("user1 == user2 : " + result1);

        // 比较user1和user2的简单类型的属性password属性（String类型，比较特殊点）
        boolean result2 = user1.getPassword()==user2.getPassword();
        System.out.println("user1.getPassword() == user2.getPassword() : " + result2);

        // 比较user1和user2的复杂类型的属性identity属性
        boolean result3 = user1.getIdentity()==user2.getIdentity();
        System.out.println("user1.getIdentity() == user2.getIdentity() : " + result3);

        System.out.println("花费时间：" + (System.currentTimeMillis()-start) + "ms");
    }

}
