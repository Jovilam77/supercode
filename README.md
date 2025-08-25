# supercode

#### 介绍
###### 超级代码生成器(嗯，想成为超级代码生成器)
###### 目前可生成SqlBean的项目风格代码，也可以生成常规的Spring+Mybatis项目风格代码，具体使用请查看GenerateConfig类的方法。
###### SqlBean ORM插件项目👉 [https://gitee.com/iJovi/vonce-sqlbean](https://gitee.com/iJovi/vonce-sqlbean "vonce-sqlbean")
###### SqlBean使用实例以及代码生成点击这里👉 [https://gitee.com/iJovi/sqlbean-example](https://gitee.com/iJovi/sqlbean-example "sqlbean-example")

#### 快速开始

###### 1.引入Maven依赖

	<dependency>
		<groupId>cn.vonce</groupId>
		<artifactId>supercode-core</artifactId>
		<version>1.0.2-beta1</version>
	</dependency>


###### 2.使用方式一（根据实体类生成代码）
```java
public class GenCode {

    public static void main(String[] args) {
        //生成配置
        GenerateConfig config = new GenerateConfig();
        config.setAuthor("Jovi");
        config.setEmail("imjovi@qq.com");
        config.setBasePackage("cn.vonce.sqlbean.example");
        //设置实体类继承的父类
        config.setBaseClass(BaseEntity.class);
        config.setPrefix("t_");
//        config.setMultiProject(true);
        //代码生成
        GenerateHelper.build(config, DbType.MySQL, "cn.vonce.sqlbean.example.model.entity");
    }

}
```
###### 3.使用方式二（根据数据库表生成代码,此方式目前需要启动项目）
```java
@RestController
public class GenCode {

    @PostMapping("buildCode")
    public Result buildCode() {
        //生成配置
        GenerateConfig config = new GenerateConfig();
        config.setAuthor("Jovi");
        config.setEmail("imjovi@qq.com");
        config.setBasePackage("com.vonce.test");
        //设置实体类继承的父类
        config.setBaseClass(BaseEntity.class);
        config.setPrefix("t_");
//        config.setMultiProject(true);
        //代码生成
        GenerateHelper.build(config, userService);
        return Result.success();
    }
    
}
```