package mortal.learn.java.data;

class Storage {
	static Object class_variable;			//类变量
	Object instance_variable;				//实例变量
	static final Object class_constant ;	//类常量
	final Object instance_constant;			//实例变量
	
	static {
		class_constant = null;
	}
	{
		instance_constant = null;
	}
	
}
