package com.myself.io.codec;

import com.myself.dto.UserDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author: wangteng
 * @description: 编解码对比实验
 * @date:2018/6/9
 */
public class CodecCompare {


    public static void main(String[] args) {
        try {
            System.out.println("-------------------------------------------");
            System.out.println("----------序列化之后码流大小对比--------------");
            System.out.println("-------------------------------------------");

            // jdk序列化之后的码流大小
            byte[] bytes1 = getJdkSerializeBytes();
            System.out.println("jdk序列化之后的码流大小：" + bytes1.length);

            // 二进制码方式序列化之后的码流大小
            byte[] bytes2 = getBinaryCodecBytes();
            System.out.println("二进制码方式序列化之后的码流大小：" + bytes2.length);



            System.out.println("-------------------------------------------");
            System.out.println("--------------序列化之的性能对比-------------");
            System.out.println("-------------------------------------------");

            int loop = 1000000;

            long startTime1 = System.currentTimeMillis();
            for (int i=0; i<loop; i++){
                getJdkSerializeBytes();
            }
            System.out.println("jdk序列化花费时间（ms）：" + (System.currentTimeMillis() - startTime1));

            long startTime2 = System.currentTimeMillis();
            for (int i=0; i<loop; i++){
                getBinaryCodecBytes();
            }
            System.out.println("二进制码方式序列化花费时间（ms）：" + (System.currentTimeMillis() - startTime2));

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * jdk序列化（Object -> byte[]）
     * @return
     * @throws IOException
     */
    private static byte[] getJdkSerializeBytes() throws IOException {
        UserDto dto = new UserDto();
        dto.setPassword("123456");
        dto.setUserName("test");
        dto.setAge(10);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(dto);
        oos.flush();
        oos.close();

        return bos.toByteArray();
    }

    /**
     * 二进制码方式序列化（Object -> byte[]）
     * @return
     */
    private static byte[] getBinaryCodecBytes(){
        UserDto dto = new UserDto();
        dto.setPassword("123456");
        dto.setUserName("test");
        dto.setAge(10);

        byte[] nameBytes = dto.getUserName().getBytes();
        byte[] pwdBytes = dto.getPassword().getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(nameBytes.length);
        buffer.put(nameBytes);
        buffer.putInt(pwdBytes.length);
        buffer.put(pwdBytes);
        buffer.putInt(dto.getAge());

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return bytes;
    }


}
