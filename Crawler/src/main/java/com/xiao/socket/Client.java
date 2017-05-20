package com.xiao.socket;

/**
 * Created by xiaojie on 17/4/24.
 */

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class Client {

    private static Logger logger = Logger.getLogger(Client.class);
    
    /**
     * Socket客户端
     */
    public static void main(String[] args) {

        for(int i = 0; i < 10; i++) {

            /* 睡眠1毫秒 */
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final int num = i;

//            new Thread(new Runnable() {
//                public void run() {
                    try {
                        //创建Socket对象
                        Socket socket = new Socket("localhost", 8887);

                        //根据输入输出流和服务端连接
                        OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
                        PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流

                        /* 构造假数据 */
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
                        jsonObject.put("name", "jacky" + num);
                        jsonObject.put("t", System.currentTimeMillis());

                        printWriter.print(jsonObject.toString());
                        printWriter.flush();
                        socket.shutdownOutput();//关闭输出流

                        InputStream inputStream = socket.getInputStream();//获取一个输入流，接收服务端的信息
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//包装成字符流，提高效率
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//缓冲区
                        String info = "";
                        String temp = null;//临时变量
                        while ((temp = bufferedReader.readLine()) != null) {
                            info += temp;
                            logger.info("客户端接收服务端发送信息：" + info);
                        }

                        //关闭相对应的资源
                        bufferedReader.close();
                        inputStream.close();
                        printWriter.close();
                        outputStream.close();
                        socket.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                }
//            }).start();

        }

    }

}
