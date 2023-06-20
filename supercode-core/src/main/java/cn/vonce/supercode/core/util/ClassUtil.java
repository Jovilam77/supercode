package cn.vonce.supercode.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Jovi
 * @email imjovi@qq.com
 * @date 2023/6/20 14:48
 */
public class ClassUtil {

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                //对比协议
                if ("file".equals(url.getProtocol())) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    File dir = new File(filePath);
                    if (dir.exists() && dir.isDirectory()) {
                        // 自定义过滤规则
                        File[] dirFiles = dir.listFiles(file -> file.getName().endsWith(".class"));
                        for (File file : dirFiles) {
                            // 如果是java类文件 去掉后面的.class 只留下类名
                            String className = file.getName().substring(0, file.getName().length() - 6);
                            try {
                                classes.add(Class.forName(packageName + '.' + className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

}
