package 聊天室;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.SocketHandler;

public class task implements Runnable {

    private Socket socket;
    final static ConcurrentHashMap<Socket, String> map = new ConcurrentHashMap<>();

    public task(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Login(socket);
            handle(socket);
        }catch (SocketException e ){
            String s3 = map.get(socket);
            map.remove(socket);
            System.out.println(s3 + "离开了");
            Thread thread = Thread.currentThread();
            thread.stop();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] bt = new byte[1024 * 8];
        int l = 0;
        Set<Socket> sockets = map.keySet();
        Socket sockett = null;
        String s1, s2;
        //读取请求

        while (true) {
            l = in.read(bt);
            String s = new String(bt, 0, l);
            switch (s) {
                case "1":
                    //响应名单
                    Collection<String> list = map.values();
                    out.write(list.toString().getBytes());
                    break;
                case "2"://读取目的用户
                    l = in.read(bt);
                    s1 = new String(bt, 0, l);
                    //寻找目的用户的socket
                    for (Socket socket1 : sockets) {
                        if (map.get(socket1).equals(s1)) {
                            sockett = socket1;
                            break;
                        }
                    }
                    //读取内容
                    l = in.read(bt);
                    s2 = new String(bt, 0, l);
                    //响应输出
                    OutputStream out1 = sockett.getOutputStream();
                    out1.write((map.get(socket) + ":" + s2).getBytes());
                    break;

                case "3":
                    l = in.read(bt);
                    s2 = new String(bt, 0, l);
                    for (Socket socket1 : sockets) {
                        OutputStream out2 = socket1.getOutputStream();
                        out2.write((map.get(socket) + ":" + s2).getBytes());
                    }
                    break;
                case "4":
                    String s3 = map.get(socket);
                    map.remove(socket);
                    System.out.println(s3 + "离开了");
                    Thread thread = Thread.currentThread();
                    thread.stop();
            }

        }

    }

    //建立连接
    private static void Login(Socket socket) throws IOException {

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] bt = new byte[1024 * 8];
            int l = in.read(bt);
            String s = new String(bt, 0, l);
            System.out.println(s + "已连接");
            map.put(socket, s);
            s = "欢迎您" + s;
            out.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
