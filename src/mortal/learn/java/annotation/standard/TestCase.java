package mortal.learn.java.annotation.standard;

import java.lang.annotation.*;

@Repeatable(TestCases.class)
@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.PACKAGE, ElementType.TYPE,
        ElementType.METHOD, ElementType.CONSTRUCTOR,
        ElementType.FIELD,ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface TestCase {
    String params();
    String expected();
}
//容器注解
@Retention(RetentionPolicy.RUNTIME)
@interface TestCases{
    TestCase[] value();
}


