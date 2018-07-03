package jdk;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

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
//        String filePath = decompressGZfile("/data/appdatas/quake-agent/4768/dicts/mergedLogFile.log");

        loopReadFile("/data/appdatas/quake-agent/4768/dicts/mergedLogFile.log");
    }


    /**
     * 循环读取文件
     */
    private static void loopReadFile(String filePath){

        try {
            long start = System.currentTimeMillis();
            String lineSeparator = System.getProperty("line.separator", "\n");
            System.out.println(lineSeparator);
            byte delimiter = lineSeparator.getBytes()[0];

            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
            long len = randomAccessFile.length();
            int size = 1024*1024*5;
            List<Byte> tempList = new ArrayList<>();
            long startIndex = 0;
            long countLine = 0L;

            while (startIndex <= len){
                // 读到文件的尾部，则从头再度
                if (startIndex>=len){
                    startIndex = 0;
                    continue;
                }

                // FileChannel提供了map方法把文件映射到虚拟内存，通常情况可以映射整个文件，如果文件比较大，可以进行分段映射(通过map方法的后面两个入参position和size控制)
                long bufferSize = len-startIndex > size? size : len-startIndex;
                MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, startIndex, bufferSize);

                // 逐个字节读取到字节数组变量中
                for (int offset=0; offset<bufferSize; offset++){
                    byte b = mappedByteBuffer.get();
                    tempList.add(b);

                    if (b==delimiter){
                        byte[] bytes = new byte[tempList.size()];
                        for (int i=0; i<tempList.size(); i++){
                            bytes[i] = tempList.get(i);
                        }
                        countLine++;
                        String oneLine = new String(bytes, "UTF-8");
                        System.out.print(oneLine);

                        tempList.clear();
                    }
                }

                startIndex = (startIndex+size)<len? startIndex+size : len;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 解压缩*.gz文件
     * @param filePath
     */
    private static String decompressGZfile(String filePath){
        long start = System.currentTimeMillis();
        BufferedWriter writer = null;
        BufferedReader reader = null;
        String newFileName = null;
        long count= 0L;

        try {
            // 父目录校验
            String fileParentDirPath = filePath.substring(0, filePath.lastIndexOf("/"));
            File f = new File(fileParentDirPath);
            if (!f.exists()){
                f.mkdir();
            }
            int index = filePath.lastIndexOf('.');
            String suffixName = filePath.substring(index+1);

            if (suffixName.equals("gz")){
                newFileName = filePath.substring(0, index);
            }
            if (newFileName==null){
                System.out.println("当前文件不是gz后缀的文件");
                return filePath;
            }

            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))));
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFileName)));

            String tempStr = null;
            while (true){
                tempStr = reader.readLine();
                if(tempStr != null){
                    System.out.println(tempStr);
                }else {
                    System.out.println("重读一次");
                    Thread.sleep(50);
                    reader.close();;
                    reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))));
                }

                if (!StringUtils.isEmpty(tempStr) && !tempStr.endsWith("\n")){
                    tempStr += "\n";
                }
                System.out.println(tempStr);
                count++;
                writer.write(tempStr);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null){
                    writer.flush();;
                    writer.close();
                }
                if (reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("总行数：" + count);
        System.out.println("waste time : " + (System.currentTimeMillis() - start));
        return newFileName;
    }


}
