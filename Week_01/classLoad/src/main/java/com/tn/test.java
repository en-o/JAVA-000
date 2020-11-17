package com.tn;

import sun.management.snmp.jvmmib.JvmClassLoadingMeta;
import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 *  显示当前classloader加载了那些jar
 * @author tn
 * @version 1
 * @ClassName test
 * @description
 * @date 2020/11/16 23:08
 */
public class test {

    public static void main(String[] args) {
        //启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url: urLs) {
            System.out.println("===>"+url.toExternalForm());
        }
        printClassLoader("扩展类加载器",test.class.getClassLoader().getParent());
        printURLForClassLoader(test.class.getClassLoader().getParent());

        printClassLoader("应用类加载器",test.class.getClassLoader());
        printURLForClassLoader(test.class.getClassLoader());
    }

    /**
     * 打印 jar地址
     * @param name
     * @param classLoader
     */
    public static void printClassLoader(String name, ClassLoader classLoader){
        if(classLoader != null){
            System.out.println(name + "classLoader ===> "+ classLoader.toString());
        }else {
            System.out.println(name + "classLoader ===> null");
        }
    }


    /**
     * 打印jar路径
     * @param loader
     */
    public static void printURLForClassLoader(ClassLoader loader){
        Object ucp = insightField(loader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList)path;
        for (Object p : ps) {
            System.out.println(" ===> " + p.toString());
        }
    }

    public static Object insightField(Object obj, String fName) {
        try {
            Field f = null;
            if(obj instanceof URLClassLoader){
                f = URLClassLoader.class.getDeclaredField(fName);
            }else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
