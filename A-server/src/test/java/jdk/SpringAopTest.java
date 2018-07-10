package jdk;

import base.JunitBaseTest;
import com.etrip.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/6
 */
public class SpringAopTest extends JunitBaseTest{
    @Autowired
    private TestService testService;

    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("11111111111");
        testService.test("aaaaaaaaa", 1L, list);
    }
}
