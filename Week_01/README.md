# 0. JDK
>  java version "1.8.0_191" 
>
>  Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
>
>  Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)

# 1. ClassLoader 
- Bootstrap 
> 启动类加载器，jdk1.8.0_191/lib 
- Extension
> 拓展类加载器 extends URLClassLoader，jdk1.8.0_191/jre/lib/ext
- Application
> 应用类加载器 extends URLClassLoader , 加载java.class.path

## 2. 类加载生命周期
	1. 加载（Loading）: 找class文件
	2. 验证（Verification）: 验证格式，依赖
	3. 准备（Preparation）: 静态字段，方法表
	4. 解析（Resolution）:  符号解析为引用
	5. 初始化（Initialization）: 构造器，静态变量赋值，静态代码块
	6. 使用（use）
	7. 卸载 （unLoading）
	
## 3. URLClassLoader
> 支持从jar文件和文件夹中获取class

## 4. 自定义类加载器
###4.1 作用
- 可以class进行加密，防止反编译
- 可以用作版本隔离

### 4.2 自定义
- 继承自java.lang.ClassLoader,重写它的findClass方法
- 加解密，改变字节规则
 

