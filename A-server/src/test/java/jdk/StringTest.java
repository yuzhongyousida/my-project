package jdk;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/19
 */
public class StringTest {

    public static void main(String[] args) {
        test1();
    }


    /**
     * String的字符常量池及intern()方法
     */
    private static void test1(){
        String s1 = new String("ABC");
        String s2 = new String("ABC");

        System.out.println("s1 == s2 : " + (s1==s2));// false

        String s3 = "ABC";
        String s4 = "ABC";
        String s5 = "AB" + "C";

        System.out.println("s1 == s3 : " + (s1==s3));// false
        System.out.println("after s1.intern(), s1 == s3 : " + (s1.intern()==s3));// true
        System.out.println("s3 == s4 : " + (s3==s4));// true
        System.out.println("s3 == s5 : " + (s3==s5));// true

        String s6 = "ABC";
        String s7 = "AB";
        String s8 = s7 + "C";
        System.out.println("s6 == s8 : " + (s6==s8));// false
    }

}
