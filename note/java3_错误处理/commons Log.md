### Commons Log
Commons Log 是Apacha提供的日志模块。是一个第三放开源某块，需要下载commons-logging-1.2.java这文见
Commons Log的特点是是它通过配置文件挂接不同的日志系统，默认情况下自动搜索并使用Log4j，如果没有找到Log4j就是用JDKLogg

Commons Log 定义了6个日志级别
+ FATAL
+ ERROR
+ WARNING
+ INFO
+ DEBUG
+ TRACE
默认级别是INFO

使用commons Log只需要两个类，
+ `LogFactory.getLog` 接受Class对象，构建一个Log类对象
+ `Log`类对象记录日志
```
Log log = LogFactory.getLog(getClass());

String message = ....;
log.info(message);
log.warn(message);

```
小结：
Commons Logging是使用最广泛的日志模块；
Commons Logging的API非常简单；
Commons Logging可以自动检测并使用其他日志模块