import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义 类加载器（ClassLoader）处理 hell0.xlass
 * @author tn
 * @date  2020/10/16 22:41
 */
public class MyClassLoader extends ClassLoader{

    /**
     * 类路径
     * 用于 获取 类  字节码数组
     */
    private  String classPath="";

    public MyClassLoader(String classPath_1){
        classPath=classPath_1;
    }


    public static void main(String[] args) {
        //获取路径
        String loadClassFilePath = MyClassLoader.class
                .getResource("/Hello.xlass").getPath();
        System.out.println(System.getProperty("user.dir"));
//        System.out.println(MyClassLoader.class.getClassLoader().getResource("hello").getPath());
//        String loadClassFilePath = "F:/project/java/jike/JAVA-000/Week_01/Hello.class";

        try {
            // 获取 类对象
            Class<?> aClass =  new MyClassLoader(loadClassFilePath).findClass("Hello");
            // 获取指定方法
            Method hello = aClass.getDeclaredMethod("hello");
            // 调用方法
            hello.invoke(aClass.newInstance());
        } catch (ClassNotFoundException e) {
            System.err.printf("编译的时候在classpath中找不到对应的类而发生的错误%s",e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.printf("没有这样的方法%s",e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.printf("没有访问权限%s",e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.printf("实例化异常%s",e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.printf("调用方法或构造方法出错%s",e.getMessage());
            e.printStackTrace();
        }catch (NoClassDefFoundError e){
            System.err.printf("运行时类加载器在classpath下找不到需要加载的类%s",e.getMessage());
            e.printStackTrace();
        }catch (NullPointerException e){
            System.err.printf("空指针异常%s",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  重写 findClass
     * @param name 二进制数 - 类名
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class hello = null;
        // 获取该class文件字节码数组
        byte[] classData = getByteArray();
        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            hello = defineClass(name, classData, 0, classData.length);
        }

        return hello;
    }


    /**
     * 将文件转化为字节码数组
     * @return 返回类字节数组
     */
    private byte[] getByteArray() {
        File file = new File(classPath);
        if (file.exists()){
            try(FileInputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//                isX true xlass, false class
                String _lass = classPath.substring(classPath.lastIndexOf("."));
                if(".xlass".equals(_lass)) {
                    byte[] b = new byte[1];
                    byte[] buffer = new byte[1];
                    byte b255 = (byte) 255;
                    int n;
                    while ((n = in.read(b)) != -1) {
                        //解密
                        buffer[0] = (byte) (b255 - b[0]);
                        out.write(buffer, 0, n);
                    }

                }else {
                    byte[] buffer = new byte[1024];
                    int size = 0;
                    while ((size = in.read(buffer)) != -1) {
                        out.write(buffer, 0, size);
                    }
                }

                return out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return null;
        }
    }
}