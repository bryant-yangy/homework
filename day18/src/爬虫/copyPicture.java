package 爬虫;

import org.omg.CORBA.Current;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class copyPicture {
    public static void main(String[] args) throws IOException {
        ExecutorService service = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        //https://tieba.baidu.com/p/2256306796?red_tag=1781367364
        //<img class="BDE_Image" src="https://imgsa.baidu.com/forum/w%3D580/sign=43e292947c1ed21b79c92eed9d6fddae/6bfab2fb43166d228b3c16f2472309f79052d20a.jpg" width="560" height="314" pic_type="0">
        HttpURLConnection connection = (HttpURLConnection)
                new URL("https://tieba.baidu.com/p/2256306796?red_tag=1781367364").openConnection();
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int i = 1;
        String l;
        StringBuffer s = new StringBuffer("");
        while ((l = reader.readLine()) != null) {
            s.append(new String(l));
        }
        // System.out.println(s);
        File file = new File("图片");
        file.mkdir();
        int current = 0;
        while ((current = s.indexOf("<img class=\"BDE_Image\"", current)) != -1) {

            int end = s.indexOf(">", current);
            current++;
            String s1 = s.substring(current, end);
            int current1 = s1.indexOf("https:");
            int ends1 = -1;
            String s2 = "";
            if (current1 != -1) {
                ends1 = s1.indexOf("\"", current1);
                s2 = s1.substring(current1, ends1);
                service.submit(new task(s2, String.valueOf(i++)));
            }
        }
        service.shutdown();

    }
}
