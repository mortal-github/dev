package mortal.learn.java.errorhandle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Exception {

	public static void throwsException(String file, boolean throwable)throws IOException
	{
		Files.readAllBytes(Paths.get(file));	//调用一个抛出手擦和异常的方法时，若不捕获则应该声明受查异常
		
		if(throwable) 
			throw new IOException("throw");	//应发现错误而抛出受查异常，应该声明受查异常
	}	
	
	public static void catchException(boolean ioException,boolean fileNotFound,boolean unknownHost)
	{
		try {
			if(fileNotFound)
				throw new FileNotFoundException();
			if(unknownHost)
				throw new UnknownHostException();
			if(ioException)
				throw new IOException();
		}catch(FileNotFoundException | UnknownHostException e){	//捕获多个异常
			e.printStackTrace();
		}catch(IOException e)	//多个catch子句
		{
			e.printStackTrace();
		}
	}
		
	public static void causeException(boolean cause)throws IOException
	{
		FileNotFoundException e = new FileNotFoundException();
		if(cause)
			throw (IOException)(new IOException().initCause(e));
		else 
			throw e;
			
	}
}
