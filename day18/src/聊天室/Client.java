package 聊天室;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("已连接服务器");
        //建立连接
        System.out.println("请输入一个昵称");
        Scanner input = new Scanner(System.in);
        String s = input.next();
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        out.write(s.getBytes());
        //发送请求
        new Thread(() -> {
            while (true) {
                try {
                    byte[] bt = new byte[1024 * 8];
                    int l = 0;
                    while (true) {
                        l = in.read(bt);
                        if (l == -1) break;
                        System.out.println(new String(bt, 0, l));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

        //输出接收的消息
        new Thread(() -> {
            String s1;
            System.out.println("请选择功能：");
            System.out.println("1.查看在线人名单");
            System.out.println("2.私聊(2 人名 信息)");
            System.out.println("3.群发(3 群发信息)");
            System.out.println("4.退出");
            while (true) {

                try {
                    s1 = input.next();
                    out.write(s1.getBytes());
                    switch (s1) {
                        case "2":
                            s1 = input.next();
                            out.write(s1.getBytes());
                            s1 = input.next();
                            out.write(s1.getBytes());
                            break;
                        case "3":
                            s1 = input.next();
                            out.write(s1.getBytes());
                            break;
                        case "4":
                            System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
