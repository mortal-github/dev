package mortal.learn.java.errorhandle;

import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JDKLogging {

	private static final Logger logger = Logger.getLogger("mortal.learn.java.strengthen.JDKLogging");

	public static void main(String[] args) 
	{
		//testLoggerLevel();
		//log(Level.INFO);
		//logp(Level.INFO,"JDKLogging", "logp");

		logger.setLevel(Level.ALL);
		//filter();
		try {
			formater();
		} catch (SecurityException | IOException e) {

			e.printStackTrace();
		}
		
//		try {
//			handler(Level.ALL, Level.SEVERE, 700);
//		} catch (SecurityException | IOException e) {
//			e.printStackTrace();
//		}
	}
	public static void testLoggerLevel(int id)
	{
		//消除处理器影响
		Handler[] handlers = logger.getParent().getHandlers();
		for(Handler h : handlers)
			h.setLevel(Level.ALL);
		
		Level level ;
		//由默认配置文件控制记录级别
		level = logger.getParent().getLevel();
		System.out.println("properties file level = " + level);
		direct();
		
		//程序中控制记录级别
		level = Level.FINER;
		System.out.println("setLevel = " + level);
		logger.setLevel(level);
		direct();
		level = logger.getParent().getLevel();
		System.out.println("ParentLevel = " + level);
	}
	
	public static void direct()
	{
		logger.severe("severe");
		logger.warning("warning");
		logger.info("info");
		logger.fine("fine");
		logger.finer("finer");
		logger.finest("finest");
	}
	public static void log(Level level)
	{
		logger.log(level, "message");
		logger.log(level, "throwable", new Throwable());
		logger.log(level, "obj|{0}|", new Object());
		logger.log(level, "objs|{0}|{1}|", new Object[]{new Object(), new Object()});
	}
	public static void logp(Level level,String className, String methodName)
	{
		logger.logp(level, className, methodName, "message");
		logger.logp(level, className, methodName, "throwable", new Throwable());
		logger.logp(level, className, methodName, "obj|{0}|", new Object());
		logger.logp(level, className, methodName, "objs|{0}|{1}|", new Object[] {new Object(), new Object()});
	}
	
	public static void handler(Level logLevel, Level handleLevel, int loop) throws SecurityException, IOException
	{
		Handler console = new ConsoleHandler();
		String pattern = "%h\\log\\JDKLOG%u%%%g.txt";
		int limit = 1024;
		int count = 3;
		boolean append = false;
		Handler file = new FileHandler(pattern,limit,count, append);
		
		console.setLevel(handleLevel);
		file.setLevel(handleLevel);
	
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		logger.addHandler(console);
		logger.addHandler(file);
		
		for(int i=0; i< loop; i++)
		{
			try {
				Thread.sleep(10);
				direct();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void filter()
	{
		logger.setFilter(new Filter() {
			@SuppressWarnings("unused")
			@Override
			public boolean isLoggable(LogRecord record) {
				Level level = record.getLevel();
				String loggerName = record.getLoggerName();
				
				String message = record.getMessage();
				Object[] param = record.getParameters();
				Throwable e = record.getThrown();
				String className = record.getSourceClassName();
				String methodNmae = record.getSourceMethodName();
				
				long mills = record.getMillis();
				long sequence = record.getSequenceNumber();
				long threadID = record.getThreadID();
				
				return level.intValue() >= Level.INFO.intValue();
			}
			
		});
		direct();
	}
	
	public static void formater() throws SecurityException, IOException
	{
		Handler fileHandler = new FileHandler("%h/log/formater.txt");
		logger.setUseParentHandlers(false);
		logger.addHandler(fileHandler);
		
		fileHandler.setLevel(Level.ALL);
		
		fileHandler.setFormatter(new Formatter() {
			@Override
			public String getHead(Handler h)
			{
				return "handler level = " +  h.getLevel().toString() + "\n";
				
			}
			@Override
			public String getTail(Handler h)
			{
				return "end";
			}
			@Override
			public String format(LogRecord record) {
				return formatMessage(record) +  "\n";
			}
		});
		
		direct();
		fileHandler.close();
		
	}
}
