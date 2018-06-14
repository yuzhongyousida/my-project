package jdk;

import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/13
 */
public class MappedByteBufferTest {

    /**
     * 循环读取大文件内容（RandomAccessFile + MappedByteBuffer）
     * @param args
     */
    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            byte delimiter1 = "\n".getBytes()[0];
            byte delimiter2 = "\r".getBytes()[0];

            RandomAccessFile randomAccessFile = new RandomAccessFile("/data/appdatas/quake-agent/test.text", "r");
            long len = randomAccessFile.length();
            int size = 1024*1024*5;
            List<Byte> tempList = new ArrayList<>();
            long startIndex = 0;

            while (startIndex <= len){
                // 读到文件的尾部，则从头再度
                if (startIndex>=len){
                    startIndex = 0;
                    System.out.println("============只不过是从头再来~~~");
                    continue;
                }

                // FileChannel提供了map方法把文件映射到虚拟内存，通常情况可以映射整个文件，如果文件比较大，可以进行分段映射(通过map方法的后面两个入参position和size控制)
                long bufferSize = len-1-startIndex > size? size : len-1-startIndex;
                MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, startIndex, bufferSize);

                // 逐个字节读取到字节数组变量中
                for (int offset=0; offset<bufferSize; offset++){
                    byte b = mappedByteBuffer.get();
                    tempList.add(b);

                    if (b==delimiter1 || b==delimiter2){
                        byteToStr(tempList);

                        tempList.clear();
                    }
                }

                startIndex = (startIndex+size)<len? startIndex+size : len;
            }

            System.out.println(System.currentTimeMillis() - start);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private static void byteToStr(List<Byte> tempList) throws UnsupportedEncodingException {
        byte[] bytes = new byte[tempList.size()];
        for (int i=0; i<tempList.size(); i++){
            bytes[i] = tempList.get(i);
        }

        System.out.print(new String(bytes, "UTF-8"));
    }

}
