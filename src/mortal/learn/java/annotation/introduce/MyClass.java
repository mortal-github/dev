package mortal.learn.java.annotation.introduce;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

public class MyClass {
   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Test{
      long timeout() default 0L;
   }

   @Test public void checkRandomInsertinos() { }
   @Test(timeout=23423434L)
   public void check2(){}
}
