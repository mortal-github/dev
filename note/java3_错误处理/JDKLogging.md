### JDKLogging
JDKLogging由以下几部分构成
+ 日志记录器
+ 日志处理器
+ 日志管理器
+ 日置配置文件：默认情况下存在于jre/lib/logging.properties
+ 过滤器
+ 格式化

#### 日志记录级别
+ SEVERE
+ WARNING
+ INFO
+ CONFIG
+ FINE
+ FINER
+ FINEST

#### 日志处理

**默认情况**
1. 日志记录器只记录超过其日志记录级别的记录
2. 然后将这些记录发送给自己的处理器或父处理
3. 处理器接受记录，只处理其中超过处理器的日志记录级别的的记录
>也就是说，一个记录必须超过日志记录器的级别才被记录，但是也必须超过处理器的级别才会被处理。
>也就是说如果想处理一个日志记录，那么他的级别必须同时高于日志记录器和日志处理器的功能。

**注意:** 这些日志记录的过滤功能是通过过滤器来完成。

**过滤器与附加过滤条件**
默认情况下，过滤器更具日志记录的级别进行过滤。
每个日志记录器和处理器都可以有一个可选的过滤器来完成**附加过滤的功能**。
`setFilter(Filter filter)`方法可设置附加的过滤器。
**注意：**同一时刻最多只能有一个过滤器。
通过实现Filter接口实现自定义过滤器
```java
public interface Filter{
    boolean isLoggable(LogRecord record);//返回true则记录日志记录
}
```

**消息与格式化器**
处理器实质上是通过调用格式化器来格式化日志记录，然后再输出。
可以通过实现`Formatter`接口自定义格式化器，
`setFormatter`方法将格式化器安装到处理器。
`fortmat`方法中可能需要调用`String formatMesage(LogRecord record)`方法对记录中的部分消息格式化、参数替换和本地化操作。
```java
public interface Formatter{
      //再已格式化的记录前后加上一个头部和尾部
    String getHead(Handler h);
    String getTail(Handler h);//getTail只在文档记录有效

    String format(LogRecord record);//对记录的信息进行格式化
}

```

#### 日志管理器与配置文件
默认的日志配置文件：jre/lib/logging.properties
可以自定义日志配置文件：
1. 需要将`java.util.logging.config.file`特性设置为配置文件的储存位置
2. 然后使用如此使用命令启动程序:`java -Djava.util.logging.config.file=confiFile MainClass`。

重新初始化日志管理
日志管理器再VM启动过程中初始化，在main执行之前完成。
如果在main中调用`System.setProperty("java.util.logging.config.fliel", file)`, 就会调用`LogManager.readConfiguration()`重新初始化日志管理器。

改变日志管理器
日志属性文件由`java.util.logging.LogManager`类来处理。
+ 可以通过将`java.util.logging.manager`系统属性指定其的**某个子类**来做日志管理器

跳过日志属性文件初始化
将`java.util.logging.config.class`系统属性设置为某个类名，就可跳过日志属性文件初始化。然后该类在通过其他方式设定日志管理属性

#### 配置文件的属性
+ `.level = INFO` 设置默认的日志记录级别
+ `com.mycompan.myapp.level=FINE` 在日志记录器名后面**添加后缀`.level`**指定特定日志记录器的日志记录级别，覆盖默认日志记录级别
+ `java.util.logging.ConsoleHandler.level=INFO` 默认的控制台处理器的日志记录级别
+ 文件处理器配置参数
    + `java.util.logging.FileHandler.level` 处理器级别，默认Level.ALL
    + `java.util.logging.FileHandler.append` 是否追加记录到已有文件还是为每个运行的程序打开一个新文件，默认false
    + `java.util.logging.FileHandler.limit` 文件写入的近似最大字节数，超过则打开新文件。0表示无限制，默认为5000
    + `java.util.logging.FileHandler.count`在循环序列的日志记录数量，1表示不循环
    + `java.util.logging.FileHandler.pattern` 日志文件名的模式
    + `java.util.logging.FileHandler.filter` 过滤器，默认没有使用过滤器
    + `java.util.logging.FileHandler.formatter` 记录格式器，java.util.logging.XMLFormatter
    + `java.util.logging.FileHandler.encoding`使用的字符编码，默认平台的编码。

日志记录文件模式变量
|变量|描述|
|:-|:-:|
|%h|系统属性user.home的值|
|%t|系统临时目录|
|%u|用于解决冲突的唯一编号, 使用了它之后相当于append=false(即使指定append为true)|
|%g|为循环日志记录生成的数值。（但使用循环功能，即使模式不包括%g，也会使用后缀%g|
|%%|%字符|

####  日志记录器
全局日志记录器：`Logger.getGlobal()`,返回一个全局日志记录器
自定义日志记录器：`Logger.getLogger("com.mycompany.myapp")`,getLogger方法**创建或获取**日志记录器。
**注意:**未被任何变量引用的日志记录器可能会被回收，到时候就不能在通过getLogger方法获取之前的日志记录器，而是创建一个新的日志记录器。

日志记录器层次：日志记录器名与包名类似，具有层级结构，日志记录器的层次性更强，父与子之间共享某些属性，比如子记录器继父记录器的级别

设置日志记录级别：`logger.setLevel(Level.FINE)`可设置日志记录级别。即覆盖日志配置文件的记录级别。

**直接记录**指定级别的记录
```
Logger logger = Logger.getLogger("com.company.app");
String message = ...;
logger.severe(message);     //SEVERE级别
logger.warning(message);     //WARNING级别
logger.info(message);     //INFO级别
logger.config(message);     //CONFIG级别
logger.fine(message);     //FINE级别
logger.finer(message);     //FINEST级别
logger.finest(message);     //FINEST级别
```
**记录异常**
```java
String classname = ...;
String methodname = ...;
Throwable throwable = ...;
logger.throwing(classname,methodname, throwable)
//记录一条FINER级别的记录，和以THROW开始的信息。
```

**log方法**记录日志
```
Level level = ...;
String message = ...;
Throwable throwable = ...;
Object obj = ...;
Object[] objs = ...;
logger.log(level, message); //指定记录的级别，并记录消息
logger.log(level, message, throwable); //指定记录级别，记录消息和异常

//记录对象，消息中必须包含格式化占位符{0}、{1}等。
logger.log(level, message , obj);  
logger.log(level, message , objs);    
```
**Logp方法**明确记录类和方法的位置
当执行过程被优化之后，日志记录器无法自动获取原来的类和方法的位置。
故需要**logp方法显示指定**类和方法的位置.
其余用法与lop方法相同

```java
logger.logp(level,classname,methodname, message)；
logger.logp(level,classname,methodname, message, throwable);
logger.logp(level,classname,methodname, message, obj);
logger.logp(level,classname,methodname, message, objs);
```

**跟踪执行流**
下面调用将用将生成FINER级别和以字符串ENTRY和RETURN开始的日志记录
放在方法的进出位置，记录方法的执行
```java
Object param;
Object[] params;
Object result;
logger.entering(classname, methodname);
logger.entering(classname, methodname, param);
logger.entering(classname, methodname, params);
logger.exiting(classname, methodname);
logger.exiting(classname, methodname, result);
//记录一个描述进入/退出方法的日志记录。应该包括参数和返回值。
```

**非记录方法**
```
Level getLevel()
void setLevel(Level l)
//获得和设置这个日志记录器的级别

Logger getParent()
void setParent(Logger l)
//获得和设置这个日志记录器的父日志记录器

Handler[] getHandlers()
//获得这个日志处理器的所有处理器

void addHandler(Handler h)
void removeHandler(Handler h)
//增加或删除日志记录器中的一个处理器

boolean getUseParentHandlers()
void setUseParentHandlers(boolean b)
//获得或设置use parent handlers 属性。true则会将记录发送给父处理器

Filter getFilter()
void setFilter()
//获得和设置这个日志记录器的过滤器

```

#### 处理器
日志处理器也有日志记录级别。
默认情况下，日志记录器将记录**发送到自己的处理器和父处理器**。
处理器继承于Handler类

**控制台处理器**
自定义的日志记录器是原始日志记录器("命名为“”")的子类,他有一个`ConsoalHandler`处理，即默认情况下：日志记录器将记录发送到`ConsoleHandler`中，由他输出到system.err
```java
java.util.logging.ConsoleHandler
ConsoleHandler()
```

**安装自定义处理器**
可以绕过配置文件安装自己的处理器
```
logger.setUerParentHandlers(false);

Handler handler = new ConsoleHandler(); //新建一个控制处理器
handler.setLevel(level);        //设置日志处理级别
logger.addHandler(handler);     //将izhi处理器安装到日志记录器
```
**注意:**
一般自定义安装了控制台处理器后，为了避免在控制台看到重复消息(原始记录器的处理器也会接受子记录器的记录)，一般都会**禁止日志记录器发送消息到父处理器**
`logger.setUerParentHandlers(false)；`

**文件处理器**
`FileHandler`可将日志记录发送到文件
配置参数请看上面内容
```java
FileHandler handler = new FileHandler();
logger.addHandler(handler);
```

可以在程序中指定文件处理器的配置参数
```java
FileHandler(Stirng pattern)
FileHandler(String pattern, boolean append)
FileHandler(String pattern, int limit, int count)
FileHandler(String pattern, int limit, int count, boolean append)
```

**套接字处理器**
`SocketHandler`将记录发送到特定的主机和端口。

**自定义处理器**
可以通过扩展`Handler`或`StreamHandler`类自定意义处理器

Handler类：
```java
java.util.logging.Handler

abstract void publish(LogRecord record)
//将日志记录发送到希望的目的地
abstract void flush()
//刷新已缓冲的数据
abstract void close()
//刷新所有已缓冲的数据，并释放相关资源

Filter getFilter()
void setFilter(Filter f)

Formatter getFormatter()
void setFormater(Formatter f)

Level getLevel()
void setLevel()
```

扩展StreamHandler只需要在构造器调用setOutPutStream方法传入一个OutPutStream()即可。
因为处理器会缓存记录，所以需要覆盖publish方法，以便在获得记录后刷新缓冲区
```java
class WindowHandler extends StreamHandler{
    public WindowHandler()
    {
        final JTextArea output = new JTextArea();
        this.setOutputStream(new
        OutputStream(){
            public void write(int b){}//not called
            public vodi write(byte[] b, int off, int len)
            {
                output.append(new String(b,off,len));
            }
        });
    }

    @Override
    public void publis(LogRecord record)
    {
        super.publish(record);
        flush();
    }
}

```

#### 日志记录对象(LogRecord)
这是一个日志记录的封装对象，在过滤器和格式化器中需要分析这个对象
```java
Level getLevel()
String getLoggerName()

ReSourceBundle getResourceBundle()
String getResourceBundleName()
//获得用于本地消息的资源包或资源包的名字，没有获得则返回null。

String getMessage()
//获得本地化和格式化之前的原始消息
Object[] getParamters()
//获得参数对象，没有就获得返回null
Throwable getThrown()
//获得被抛出对象，没有返回null
String getSrouceClassName()
String getSourceMethodName()
//获得日志记录的代码区域，可能是代码提供(logp)、从运行时堆栈推断。
//如果代码提供值有误，或代码被优化，则返回值可能不正确。

long getMillis()
//获得创建时间，毫秒为单位，从1970开始
long getSequenceNumber()
//获得唯一序列号
int getThreadID()
//获得创建这个日志记录的线程的唯一ID.这些ID由LogRecrod类分配，并且与其他线程的ID无关。

```