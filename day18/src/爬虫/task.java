package 爬虫;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class task implements Runnable {
    String s1;
    String s2;

    public task(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection)
                    new URL(s1).openConnection();
            InputStream in = connection.getInputStream();
            FileOutputStream image = new FileOutputStream("图片/" + s2 + ".gif");
            byte[] buf = new byte[1024 * 8];
            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    break;
                }
                image.write(buf, 0, len);
            }
            image.close();
            System.out.println("已爬到" + s2 + "张图");
            Thread thread = Thread.currentThread();
            thread.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
