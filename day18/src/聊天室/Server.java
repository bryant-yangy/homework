package 聊天室;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.*;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("服务已启动，等待连接");
        ExecutorService service = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        while (true) {
            // 2. 调用accept 等待客户端连接
            Socket socket = serverSocket.accept();
            service.submit(new task(socket));
        }
    }
}

