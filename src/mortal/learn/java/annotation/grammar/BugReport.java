package mortal.learn.java.annotation.grammar;

import java.lang.ref.Reference;

public @interface BugReport {

    enum Status {UNCONFIRMED, CONFIRMED, FIXED, NOTABUG};

    boolean showStopper() default false;        //基本类型
    String assignedTo() default "[none]";       //Strubg
    Class<?> testCase() default Void.class;     //Class
    Status status() default Status.UNCONFIRMED; //enum
  //  Reference ref() default @Reference();       //an annotation type
    String[] reportedBy();                      //上述类型的数组
}
