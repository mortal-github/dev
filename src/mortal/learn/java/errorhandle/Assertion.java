package mortal.learn.java.errorhandle;

public class Assertion {
	
	
	public static void main(String[] args)
	{
		assertOption(true);
	}
	public static void assertOption(boolean next)
	{
		if(next)
			assert false : false;
		else
			assert false;
	}
	
}
