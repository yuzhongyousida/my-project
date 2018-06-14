package jdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/14
 */
public class FileReaderTest {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            File file = new File("/data/appdatas/quake-agent/test.text");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
            }

            System.out.println(System.currentTimeMillis() - start);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
