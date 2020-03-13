package cn.vonce.sql.processor;

import cn.vonce.common.utils.StringUtil;
import cn.vonce.sql.annotation.SqlBeanField;
import cn.vonce.sql.annotation.SqlBeanTable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.*;
import java.util.Set;

/**
 * 生成表字段常量处理器
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2020/2/26 14:21
 */
@SupportedAnnotationTypes({"cn.vonce.sql.annotation.SqlBeanCons"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SqlBeanConsProcessor extends AbstractProcessor {
    public static final String PREFIX = "Sql";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        for (TypeElement typeElement : annotations) {
            for (Element element : env.getElementsAnnotatedWith(typeElement)) {
                //包裹注解元素的元素, 也就是其父元素, 比如注解了成员变量或者成员函数, 其上层就是该类
                Element enclosingElement = element.getEnclosingElement();
                //获取父元素的全类名,用来生成报名
                String packageName = ((PackageElement) enclosingElement).getQualifiedName().toString() + ".sql";
                String className = PREFIX + element.getSimpleName();
                String tableName = element.getSimpleName().toString();
                String tableAlias = "";
                SqlBeanTable sqlBeanTable = element.getAnnotation(SqlBeanTable.class);
                if (sqlBeanTable != null) {
                    tableName = sqlBeanTable.value();
                    tableAlias = sqlBeanTable.alias();
                }
                if (StringUtil.isEmpty(tableAlias)) {
                    tableAlias = tableName;
                }
                String projectPath = System.getProperty("user.dir");
                String resPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + packageName.replace(".", File.separator) + File.separator;
                try {
                    File file = new File(resPath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    //创建Java 文件
//                    JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(resPath + className);
//                    Writer writer = javaFileObject.openWriter();
                    FileOutputStream fos = new FileOutputStream(resPath + className + ".java");
                    try {
                        PrintWriter printWriter = new PrintWriter(fos);
                        printWriter.println("package " + packageName + ";");
                        printWriter.println("import cn.vonce.sql.bean.ColumnInfo;");
                        printWriter.println("\npublic class " + className + " { ");
                        printWriter.println("    public static final String tableName = \"" + tableName + "\";");
                        printWriter.println("    public static final String tableAlias = \"" + tableAlias + "\";");
                        for (Element subElement : element.getEnclosedElements()) {
                            if (subElement.getKind().isField() && !subElement.getModifiers().contains(Modifier.STATIC)) {
                                String sqlFieldName = subElement.getSimpleName().toString();
                                SqlBeanField sqlBeanField = subElement.getAnnotation(SqlBeanField.class);
                                if (sqlBeanField != null && StringUtil.isNotEmpty(sqlBeanField.value())) {
                                    sqlFieldName = sqlBeanField.value();
                                }
                                printWriter.println("    public static final ColumnInfo " + sqlFieldName + " = new ColumnInfo(\"" + tableAlias + "\",\"" + sqlFieldName + "\");");
                            }
                        }
                        printWriter.println("}");
                        printWriter.flush();
                    } finally {
                        fos.close();
                    }
                } catch (IOException e1) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            e1.toString());
                }
            }
        }
        return true;
    }
}
