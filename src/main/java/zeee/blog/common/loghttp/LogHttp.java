package zeee.blog.common.loghttp;

import java.lang.annotation.*;

/**
 * @author wz
 * @date 2022/8/22
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogHttp {
}

/*
=======================================
@Inherited
作用
如果一个类用上了@Inherited修饰的注解，那么其子类也会继承这个注解
@Inherited作为java的原生注解之一，表示其标记的注解允许被继承，接口实现除外。

注意
接口用上个@Inherited修饰的注解，其实现类不会继承这个注解
父类的方法用了@Inherited修饰的注解，子类也不会继承这个注解

=======================================
@Retention(RetentionPolicy.RUNTIME)
注解按生命周期来划分可分为3类：

1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；

这3个生命周期分别对应于：Java源文件(.java文件) ---> .class文件 ---> 内存中的字节码。

那怎么来选择合适的注解生命周期呢？

首先要明确生命周期长度 SOURCE < CLASS < RUNTIME ，所以前者能作用的地方后者一定也能作用。一般如果需要在运行时去动态获取注解信息，那只能用 RUNTIME 注解；如果要在编译时进行一些预处理操作，比如生成一些辅助代码（如 ButterKnife），就用 CLASS注解；如果只是做一些检查性的操作，比如 @Override 和 @SuppressWarnings，则可选用 SOURCE 注解。

=======================================
@Target({ElementType.METHOD})
作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
取值(ElementType)有：
　　　　1.CONSTRUCTOR:用于描述构造器
　　　　2.FIELD:用于描述域
　　　　3.LOCAL_VARIABLE:用于描述局部变量
　　　　4.METHOD:用于描述方法
　　　　5.PACKAGE:用于描述包
　　　　6.PARAMETER:用于描述参数
　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */
