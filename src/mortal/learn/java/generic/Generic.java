package mortal.learn.java.generic;

public class Generic<T,U> {
	T t;
	U u;
	
	public Generic(T t, U u)
	{
		this.t = t;
		this.u = u;
	}
	
	public T getT()
	{
		return this.t;
	}
	public U getU()
	{
		return this.u;
	}
	
	//泛型方法
	public static <S> S getS(S s)
	{
		return s;
	}
	
	//类型变量限定
	public static <S extends Comparable<?>> S getAllS(S s)
	{
		return s;
	}
	//类限定必须作为限定列表的第一个
	public static <T,U, S extends Generic<T,U> & Comparable<?> > S getAllSs(S s) {
								  //限定列表................//
		return s;
	}
	
	public static void main(String[] args)
	{
		Generic<String, Integer> generic = new Generic<>("钟景文", 21);
		String name = generic.getT();
		int age = generic.getU();
		System.out.println(name + " = " + age);
		
		double real = Generic.getS(2.1);
		System.out.println("real = " + real);
		
	}
	
	public static void swap(Pair<?> p)
	{
		swapT(p);//通配符捕获
	}
	public static <T> void swapT(Pair<T> p){}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void variableType()
	{
		Pair<?> p1;//无限定通配符，只能做返回值，且返回Object
		Pair<? extends Comparable> p2 ;//子类型限定，只能做返回值
		Pair<? super Comparable> p3; //超类型限定，只能做传入参数。
		
	}
	
}

class Pair<T>
{
	
}
