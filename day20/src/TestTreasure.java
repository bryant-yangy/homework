import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestTreasure {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // E:\9.22实训班共享\笔记资料
        // 类加载器, 作用：加载一个不在classpath下的类
        ClassLoader cl = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    FileInputStream in = new FileInputStream("D:\\JAVA\\homework\\day20\\Treasure.class");
                    byte[] bytes = new byte[1024 * 8];
                    int len = in.read(bytes);

                    // 调用父类的方法根据字节数组加载类
                    return defineClass(name, bytes, 0, len);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        //[Ljava.lang.annotation.Annotation;@6f94fa3e
        Class<?> clazz = cl.loadClass("com.westos.Treasure"); // 根据类名加载类, 得到类对象
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        declaredConstructors[0].setAccessible(true);
        Object o = declaredConstructors[0].newInstance();
        Method[] methods = clazz.getMethods();
        if (!methods[0].getAnnotations().toString().equals(methods[1].getAnnotations().toString()) &&
                !methods[0].getAnnotations().toString().equals(methods[2].getAnnotations().toString())) {
            methods[0].invoke(o);
            System.out.println("aaaa");
        }
        for (int i = 0; i < methods.length - 1; i++) {
            if (!methods[i].getAnnotations().toString().equals(methods[i + 1].getAnnotations().toString())) {
                methods[i + 1].invoke(o);
                break;
            }
        }
//        for (Method method : clazz.getMethods()) {
//            if(method.getAnnotations().toString().equals("[Ljava.lang.annotation.Annotation;@6f94fa3e")){
//                method.invoke(o);
//            }
//        }
    }
}
