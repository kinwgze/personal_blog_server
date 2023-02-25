# SpringBoot定时任务@Scheduled注解详解

项目开发中，经常会遇到定时任务的场景，Spring提供了`@Scheduled`注解，方便进行定时任务的开发

## 1.使用方法

要使用`@Scheduled`注解，首先需要在启动类添加`@EnableScheduling`，启用Spring的计划任务执行功能，这样可以在容器中的任何Spring管理的bean上检测`@Scheduled`注解，执行计划任务。

```java
@Component
@EnableScheduling
public class TaskUtils {
// 添加定时任务
@Scheduled(cron = "0 */1 * * *  ?") // cron 表达式，每隔1分钟执行一次
  public void doTask(){
      System.out.println("定时插入新订单");
  }
}
```

`@EnableScheduling` 注解的作用是发现注解`@Scheduled`的任务并后台执行,可以在启动类上注解也可以在当前文件。`@Scheduled`用于标注这个方法是一个定时任务的方法

```
cron表达式含义

“0 0 12 * * ?”                每天中午12点触发

“0 30 10 * * ?”               每天上午10:30触发

“0 * 14 * * ?”                在每天下午2点到下午2:59期间的每1分钟触发

“0 0/10 14 * * ?”             在每天下午2点到下午2:55期间的每10分钟触发

“0 0/5 14,18 * * ?”           在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发

“0 0-5 14 * * ?”              在每天下午2点到下午2:05期间的每1分钟触发

“0 10,44 14 ? 3 WED”          每年三月的星期三的下午2:10和2:44触发

“0 15 10 ? * MON-FRI”         周一至周五的上午10:15触发

“0 15 10 15 * ?”              每月15日上午10:15触发

“0 15 10 L * ?”               每月最后一日的上午10:15触发

“0 15 10 ? * 6L”              每月的最后一个星期五上午10:15触发

“0 15 10 ? * 6L 2002-2005”    2002年至2005年的每月的最后一个星期五上午10:15触发

“0 15 10 ? * 6#3”             每月的第三个星期五上午10:15触发

0 6 * * *                     每天早上6点

0 /2 * *                      每两个小时

0 23-7/2，8 * * *             晚上11点到早上8点之间每两个小时，早上八点

0 11 4 * 1-3                  每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
```

## 2.@Schedule注解一览

`@Scheduled`注解共有8个属性（其中有3组只是不同类型的相同配置）和一个常量CRON_DISABLED，源码如下：

```java
/**
 * 标记要调度的方法的注释。 必须准确指定 cron、fixedDelay 或 fixedRate 属性之一。带注释的方法必须没有参数。    
 * 它通常有一个 void 返回类型； 如果不是，则在通过调度程序调用时将忽略返回值。@Scheduled 注解的处理是通过注册
 * 一个 ScheduledAnnotationBeanPostProcessor 来执行的。 这可以手动完成，或者更方便的是，通过 
 * <task:annotation-driven/> 元素或 @EnableScheduling 注释。
 * 此注释可用作元注释以创建具有属性覆盖的自定义组合注释。
 * @since 3.0
 * @see EnableScheduling
 * @see ScheduledAnnotationBeanPostProcessor
 * @see Schedules
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Schedules.class)
public @interface Scheduled {

    /**
     * 指示禁用触发器的特殊 cron 表达式值：“-”。
     * 这主要用于 ${...} 占位符，允许外部禁用相应的计划方法。
     * 
     * @since 5.1
     * @see ScheduledTaskRegistrar#CRON_DISABLED
     */
    String CRON_DISABLED = ScheduledTaskRegistrar.CRON_DISABLED;

    /**
     * 一个类似 cron 的表达式，扩展了通常的 UN*X 定义以包括秒、分、小时、月中的某一天、月份和星期几的触发器。
     * 例如，“0 * * * * MON-FRI”表示工作日每分钟一次（在分钟的顶部 - 第 0 秒）。
     * 
     * 从左到右读取的字段解释如下：
     * <ul>
     * <li>second</li>
     * <li>minute</li>
     * <li>hour</li>
     * <li>day of month</li>
     * <li>month</li>
     * <li>day of week</li>
     * </ul>
     * 特殊值“-”表示禁用的 cron 触发器，主要用于由 ${...} 占位符解析的外部指定值。
     * @return 返回一个可以解析为 cron 计划的表达式
     * @see org.springframework.scheduling.support.CronSequenceGenerator
     */
    String cron() default "";

    /**
     * 将解析 cron 表达式的时区。 默认:""（即使用服务器的本地时区）。
     * 
     * @return java.util.TimeZone.getTimeZone(String) 接受的区域 ID，或指示服务器默认时区的空字符串
     * @since 4.0
     * @see org.springframework.scheduling.support.CronTrigger#CronTrigger(String, java.util.TimeZone)
     * @see java.util.TimeZone
     */
    String zone() default "";

    /**
     * 在上次调用结束和下一次调用开始之间的固定时间段，单位：毫秒，默认：-1(不延迟)
     * 
     * @return 延迟时长，单位：毫秒
     */
    long fixedDelay() default -1;

    /**
     * 上次调用的结束和下一次调用的开始之间固定时间间隔字符串，单位：毫秒。 
     * 
     * @return 延迟值字符串，单位：毫秒，例如占位符或
     *         {@link java.time.Duration#parse java.time.Duration} 兼容值
     * @since 3.2.2
     */
    String fixedDelayString() default "";

    /**
     * 在调用之间的固定时间段，单位：毫秒。
     * 
     * @return 以毫秒为单位的周期
     */
    long fixedRate() default -1;

    /**
     * 在调用之间的固定时间段字符串，单位：毫秒。
     * 
     * @return 延迟值字符串，单位：毫秒，例如占位符或 
     *         {@link java.time.Duration#parse java.time.Duration} 兼容值
     * @since 3.2.2
     */
    String fixedRateString() default "";

    /**
     * 在第一次执行 fixedRate 或 fixedDelay 任务之前延迟的毫秒数。
     * 
     * @return 初始延迟值，单位：毫秒
     * @since 3.2
     */
    long initialDelay() default -1;

    /**
     * 在第一次执行 fixedRate 或 fixedDelay 任务之前延迟的毫秒数字符串。
     * @return 初始延迟值字符串，单位：毫秒，例如占位符或符合 java.time.Duration 的值
     * @since 3.2.2
     */
    String initialDelayString() default "";

}
```

## 3.参数详解

### (1) corn

该参数接收一个cron表达式字符串，字符串以5或6个空格隔开，分开共6或7个域，每一个域代表一个含义,，其顺序和含义见对应注释说明，如下：

> [秒] [分] [小时] [日] [月] [周] [年]
其中，[年]不是必须的域，可以省略[年]，省略[年]则一共6个域。

| 域   | 必填  | 值的范围 | 允许的通配符 |
| --- | --- | --- | --- |
| 秒   | 是   | 0-59 | , - * / |
| 分   | 是   | 0-59 | , - * / |
| 时   | 是   | 0-23 | , - * / |
| 日   | 是   | 1-31 | , - * ? / L W |
| 月   | 是   | 1-12 / JAN-DEC | , - * / |
| 周   | 是   | 1-7 or SUN-SAT | , - * ? / L # |
| 年   | **否** | 1970-2099 | , - * / |

**通配符说明**：

`*`： 所有值。 例如：在 [分] 上设置 * 则每一分钟都会触发执行。

`?`： 不指定值。使用的场景为不需要关心当前设置这个字段的值。例如:要在每月的1号触发执行，而不关心是星期几，所以需要 [周] 上设置为?， 具体设置为：“0 0 0 1 * ?”。

`-`： 区间。例如：在 [时] 上设置 “1-3” ，则1，2，3点都会触发执行。

`,`： 指定多个值，例如在 [周] 上设置 “MON,WED,FRI” 表示周一，周三和周五触发。

`/`： 用于递增触发。如在 [秒] 上设置”5/15” 表示从5秒开始，每增15秒触发(5,20,35,50)。 在 [日] 上设置’1/3’所示每月1号开始，每隔三天触发一次。

`L`： 最后的意思（Last）。在 [日] 字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]), 在 [周] 上表示星期六，相当于”7”或”SAT”。如果在”L”前加上数字，则表示该数据的最后一个。例如在 [周] 上设置”6L”这样的格式，则表示“本月最后一个星期五”

`W`： 离指定日期的最近那个工作日(Work Day，周一至周五)。例如在[日] 上置”15W”，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为 “1W”,它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注：”W”前只能设置具体的数字，不允许区间”-“)。

`#`： 序号(表示每月的第几个周几)，例如在 [周] 上设置”6#3”表示在每月的第三个周六，注意如果指定”#5”,正好第五周没有周六，则不会触发该配置(适用于没有固定日期的节假日：如母亲节和父亲节)

**TIPS**：
1）’L’和 ‘W’可以一组合使用。如果在 [日] 上设置”LW”，则表示在本月的最后一个工作日触发
2）周字段的设置，若使用英文字母是不区分大小写的，即MON与mon相同。
3）cron属性如注释中所说，支持${ }表达式，可从spring配置文件中动态获取配置的cron表达式：

### (2) zone

时区，接收一个`java.util.TimeZone#ID`。cron表达式会基于该时区解析。默认是一个空字符串，即取服务器所在地的时区。比如我们一般使用的时区Asia/Shanghai。该字段我们一般留空。

### (3)fixedDelay / fixedDelayString

上一次执行完毕时间点之后多长时间再执行，`long`型。如：

```java
 // 上次任务执行结束后15秒再执行下一次
 @Scheduled(fixedDelay = 15000) 
```

`fixedDelayString`与 `fixedDelay`意思相同，`String`类型。唯一不同是**支持占位符**。如：

```java
 // 上次任务执行结束后15秒再执行下一次
 @Scheduled(fixedDelayString = "15000") 
```

占位符的使用 (上文中有配置：task.fixed-delay=15000)：

```java
 @Scheduled(fixedDelayString = "${task.fixed-delay}")
 void testFixedDelayString() {
    System.out.println("Task running: " + System.currentTimeMillis());
 }
```

### (4)fixedRate / fixedRateString

上一次开始执行时间点之后多长时间再执行，`long`型。如：

```java
 // 上次任务执行结束后15秒再执行下一次
 @Scheduled(fixedRate = 15000)
```

`fixedRateString`与 `fixedRate` 意思相同，`String`类型。唯一不同是**支持占位符**。

### (5)initialDelay / initialDelayString

第一次延迟多长时间后再执行，`long`型。如：

```java
 //  第一次延迟5秒后执行，之后按fixedRate的规则每10秒执行一次
 @Scheduled(initialDelay = 5000, fixedRate = 10000)
```

`initialDelayString`与 `initialDelay` 意思相同，`String`类型。唯一不同是**支持占位符**。
