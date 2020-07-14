package com.cnki.kafka.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		//系统的默认编码是GBK
        System.out.println("Default Charset=" + Charset.defaultCharset());
       
        //思路：先转为Unicode，然后转为GBK
        String str = "未审核";
       
        System.out.println(bytes2hex(str.getBytes("UTF-8")));
        System.out.println(bytes2hex(str.getBytes("GBK")));
        System.out.println(bytes2hex(str.getBytes("gb2312")));
      
	}

	public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {     
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();

    }
}
