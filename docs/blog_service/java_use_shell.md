# 2022/05/05
## Java执行shell命令
参考[java调用并执行shell脚本](https://blog.csdn.net/qq_41893274/article/details/116573250)
### 1.直接 Runtime.getRuntime().exec()方法
```java
//在单独的进程中执行指定的字符串命令。 
Process exec(String command) 
 
//在单独的进程中执行指定命令和变量。
Process exec(String[] cmdarray) 
 
//在指定环境的独立进程中执行指定命令和变量。 
Process exec(String[] cmdarray, String[] envp) 
 
//在指定环境和工作目录的独立进程中执行指定的命令和变量。
Process exec(String[] cmdarray, String[] envp, File dir) 
 
//在指定环境的单独进程中执行指定的字符串命令。
Process exec(String command, String[] envp) 
 
//在有指定环境和工作目录的独立进程中执行指定的字符串命令
Process exec(String command, String[] envp, File dir) 
```
* cmdarray: 包含所调用命令及其参数的数组。
* command: 一条指定的系统命令。
* envp: 字符串数组，其中每个元素的环境变量的设置格式为name=value；如果子进程应该继承当前进程的环境，则该参数为 null。
* dir: 子进程的工作目录；如果子进程应该继承当前进程的工作目录，则该参数为 null。

其中，其实cmdarray和command差不多，同时如果参数中如果没有envp参数或设为null，表示调用命令将在当前程序执行的环境中执行；如果没有dir参数或设为null，表示调用命令将在当前程序执行的目录中执行，因此调用到其他目录中的文件和脚本最好使用绝对路径。

细心的读者会发现，为了执行调用操作，JVM会启一个Process，所以我们可以通过调用Process类的以下方法，得知调用操作是否正确执行：
```java
//导致当前线程等待，如有必要，一直要等到由该 Process 对象表示的进程已经终止。
abstract int waitFor()
 
// 返回值是进程的出口值。根据惯例，0 表示正常终止；否则，就表示异常失败。
```

另外，调用某些Shell命令或脚本时，会有返回值，那么我们如果捕获这些返回值或输出呢？为了解决这个问题，Process类提供了：
```java
// 获取子进程的输入流。 最好对输入流进行缓冲。
abstract InputStream getInputStream()
```
### 2.经过ProcessBuilder进行调度
每个 ProcessBuilder 实例管理一个进程属性集。start()) 方法利用这些属性创建一个新的 Process 实例。start()) 方法可以从同一实例重复调用，以利用相同的或相关的属性创建新的子进程。

每个进程生成器管理这些进程属性：

* 命令  是一个字符串列表，它表示要调用的外部程序文件及其参数（如果有）。在此，表示有效的操作系统命令的字符串列表是依赖于系统的。例如，每一个总体变量，通常都要成为此列表中的元素，但有一些操作系统，希望程序能自己标记命令行字符串——在这种系统中，Java 实现可能需要命令确切地包含这两个元素。
* 环境  是从变量 到值 的依赖于系统的映射。初始值是当前进程环境的一个副本（请参阅 System.getenv()）。
* 工作目录。默认值是当前进程的当前工作目录，通常根据系统属性 user.dir 来命名。
* redirectErrorStream 属性。最初，此属性为 false，意思是子进程的标准输出和错误输出被发送给两个独立的流，这些流可以通过 Process.getInputStream()) 和 Process.getErrorStream()) 方法来访问。如果将值设置为 true，标准错误将与标准输出合并。这使得关联错误消息和相应的输出变得更容易。在此情况下，合并的数据可从 Process.getInputStream()) 返回的流读取，而从 Process.getErrorStream()) 返回的流读取将直接到达文件尾。
  修改进程构建器的属性将影响后续由该对象的 start()) 方法启动的进程，但从不会影响以前启动的进程或 Java 自身的进程。

大多数错误检查由 start()) 方法执行。可以修改对象的状态，但这样 start()) 将会失败。例如，将命令属性设置为一个空列表将不会抛出异常，除非包含了 start())。


>**注意**
>
>此类不是同步的。如果多个线程同时访问一个 ProcessBuilder，而其中至少一个线程从结构上修改了其中一个属性，它必须 保持外部同步。要利用一组明确的环境变量启动进程，在添加环境变量之前，首先调用 Map.clear()


>ProcessBuilder(List<String> command) 利用指定的操作系统程序和参数构造一个进程生成器。
>ProcessBuilder(String... command) 利用指定的操作系统程序和参数构造一个进程生成器。

```java
// 方法摘要	
List<String>	command() // 返回此进程生成器的操作系统程序和参数。
ProcessBuilder	command(List<String> command) // 设置此进程生成器的操作系统程序和参数。
ProcessBuilder	command(String... command) // 设置此进程生成器的操作系统程序和参数。
File	directory() // 返回此进程生成器的工作目录。
ProcessBuilder	directory(File directory) // 设置此进程生成器的工作目录。
Map<String,String>	environment() // 返回此进程生成器环境的字符串映射视图。
boolean	redirectErrorStream() // 通知进程生成器是否合并标准错误和标准输出。
ProcessBuilder	redirectErrorStream(boolean redirectErrorStream) // 设置此进程生成器的 redirectErrorStream 属性。
Process	start() // 使用此进程生成器的属性启动一个新进程。
```

**Runtime对比ProcessBuilder**

* Runtime方式： 此为最常见的一种运行方式，历史最悠久，使应用程序能够与其运行的环境相连接，但是在读取上还存在一些不便性，正常的输出流与错误流得分开读取。其他功能基本相同。
* ProcessBuilder：此为jdk1.5加入的，它没有将应用程序与其运行的环境相连接。这个就需要自己设置其相关的信息。但它提供了将正常流与流程流合并在一起的解决办法，只需要设置redirectErrorStream（错误流重定向到标准数据流）属性即可。

### 3.调用shell 命令
```java
public class ReadCmdLine {
    public static void main(String args[]) {
        callShellByExec("docker info");
    }
 
    /**
     * 使用 exec 调用shell脚本
     * @param shellString
     */
    public static void callShellByExec(String shellString) {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec(shellString);
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                log.error("call shell failed. error code is :" + exitValue);
            }
            // 返回值
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("mac@wxw %  " + line);
            }
        } catch (Throwable e) {
            log.error("call shell failed. " + e);
        }
    }
}
```
### 4.调用shell脚本

Java调用Shell命令和调用Shell脚本的操作一模一样。我这里介绍另外几个方面：

* 给脚本传递参数；
* 捕获调用的输出结果；
* envp的使用。
  给脚本传递参数，这个操作很简单，无非是把参数加到调用命令后面组成String，或String[]。

#### 4.1 通过ProcessBuilder进行调度

这种方法比较直观，而且参数的设置也比较方便， 比如我在实践中的代码(我隐藏了部分业务代码)：
```java
ProcessBuilder pb = new ProcessBuilder("./" + RUNNING_SHELL_FILE, param1,
                                               param2, param3);
        pb.directory(new File(SHELL_FILE_DIR));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }
 
        } catch (IOException e) {
        }
        if (runningStatus != 0) {
        }
        return;
```

这里有必要解释一下几个参数：

* RUNNING_SHELL_FILE：要运行的脚本
* SHELL_FILE_DIR：要运行的脚本所在的目录； 当然你也可以把要运行的脚本写成全路径。
* runningStatus：运行状态，0标识正常。 详细可以看java文档。
* param1, param2, param3：可以在RUNNING_SHELL_FILE脚本中直接通过1,2,$3分别拿到的参数。
#### 4.2 直接通过系统Runtime执行shell
```java
p = Runtime.getRuntime().exec(SHELL_FILE_DIR + RUNNING_SHELL_FILE + " "+param1+" "+param2+" "+param3);
p.waitFor();
```

我们发现，通过Runtime的方式并没有builder那么方便，特别是参数方面，必须自己加空格分开，因为exec会把整个字符串作为shell运行。

捕获调用输出信息，前面也提到过用Process.getInputStream()。不过，建议最好对输入流进行缓冲：
```java
BufferedReader input = 
      new BufferedReader(new InputStreamReader(process.getInputStream()));
```
另外，envp是一个String[]，并且String[]中的每一个元素的形式是：name=value。如：我的Linux系统中没有以下环境变量，但是我把它们写在Java代码中，作为envp：

```java
val=2
call=Bash Shell
```
我要调用的Shell脚本是：/root/experiment/test.sh。
```shell
#!/usr/bin/env bash
 
args=1
if [ $# -eq 1 ];then
 args=$1
 echo "The argument is: $args"
fi
 
echo "This is a $call"
start=`date +%s`
sleep 3s
end=`date +%s`
cost=$((($end - $start) * $args * $val))
echo "Cost Time: $cost"
```

Java调用代码是：
```java
    private void callScript(String script,String args,String...workspace){
        try{
        String cmd="sh "+script+" "+args;
        File dir=null;
        if(workspace[0]!=null){
        dir=new File(workspace[0]);
        System.out.println(workspace[0]);
        }
        String[]evnp={"val=2","call=Bash Shell"};
        process=Runtime.getRuntime().exec(cmd,evnp,dir);
        BufferedReader input=new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line="";
        while((line=input.readLine())!=null){
        System.out.println(line);
        }
        input.close();
        }
        catch(Exception e){
        e.printStackTrace();
        }
        }

public static void main(String[]args){
        // TODO Auto-generated method stub
        CallShell call=new CallShell();
        call.callScript("test.sh","4","/root/experiment/");
        }
```

### 5.可能存在的问题以及解决方法
如果你觉得通过上面就能满足你的需求，那么可能是要碰壁了。你会遇到以下情况。

#### 5.1 没权限运行

要执行包里面的shell脚本， 解压出来了之后，发现执行不了。 那么就按照上面的方法授权吧
```java
ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", tempFile.getPath());
            Process process = builder.start();
            int rc = process.waitFor();
```
#### 5.2 java进行一直等待shell返回

这个问题估计更加经常遇到。 原因是， shell脚本中有echo或者print输出， 导致缓冲区被用完了! 为了避免这种情况， 一定要把缓冲区读一下， 好处就是，可以对shell的具体运行状态进行log出来。 比如上面我的例子中我会变成：
```java
ProcessBuilder pb = 
          new ProcessBuilder("./" + RUNNING_SHELL_FILE, keyword.trim(),
                                  taskId.toString(), fileName);
        pb.directory(new File(CASPERJS_FILE_DIR));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                LOG.error(s);
            }
            while ((s = stdError.readLine()) != null) {
                LOG.error(s);
            }
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }
```

记得在start()之后， waitFor（）之前把缓冲区读出来打log， 就可以看到你的shell为什么会没有按照预期运行。 这个还有一个好处是，可以读shell里面输出的结果， 方便java代码进一步操作。

也许你还会遇到这个问题，明明手工可以运行的命令，java调用的shell中某一些命令居然不能执行，报错：命令不存在！

比如我在使用casperjs的时候，手工去执行shell明明是可以执行的，但是java调用的时候，发现总是出错。 通过读取缓冲区就能发现错误日志了。 我发现即便自己把安装的casperjs的bin已经加入了path中（/etc/profile, 各种bashrc中）还不够。 比如：
```shell
export NODE_HOME="/home/admin/node"
export CASPERJS_HOME="/home/admin/casperjs"
export PHANTOMJS_HOME="/home/admin/phantomjs"
export PATH=$PATH:$JAVA_HOME/bin:/root/bin:$NODE_HOME/bin:$CASPERJS_HOME/bin:$PHANTOMJS_HOME/bin
```
原来是因为java在调用shell的时候，默认用的是系统的/bin/下的指令。特别是你用root权限运行的时候。 这时候，你要在/bin下加软链了。针对我上面的例子，就要在/bin下加软链：
```shell
ln -s /home/admin/casperjs/bin/casperjs casperjs;
ln -s /home/admin/node/bin/node node;
ln -s /home/admin/phantomjs/bin/phantomjs phantomjs;
```
这样，问题就可以解决了。

#### 5.3 如果是通过java调用shell进行打包，那么要注意路径的问题

因为shell里面tar的压缩和解压可不能直接写
```shell
tar -zcf /home/admin/data/result.tar.gz /home/admin/data/result
```
直接给你报错，因为tar的压缩源必须到路径下面， 因此可以写成
```shell
tar -zcf /home/admin/data/result.tar.gz -C /home/admin/data/ result
```
#### 5.4如果我的shell是在jar包中怎么办？

答案是：解压出来。再按照上面指示进行操作。
```java
String jarPath = findClassJarPath(ClassLoaderUtil.class);
     JarFile topLevelJarFile = null;
        try {
            topLevelJarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = topLevelJarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".sh")) {
                    对你的shell文件进行处理
                }
            }
```
对文件处理的方法就简单了，直接touch一个临时文件，然后把数据流写入，代码：
```java
FileUtils.touch(tempjline);
tempjline.deleteOnExit();
FileOutputStream fos = new FileOutputStream(tempjline);
IOUtils.copy(ClassLoaderUtil.class.getResourceAsStream(r), fos);
fos.close();
```

记得一点， 不要过度地依赖缓冲区进行线程之间的通信。
