package gnnt.mebs.base.http.xml;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface XMLSerializedName {

    /**
     * 返回XML映射字段名称
     *
     * @return XML映射字段名称
     */
    String value();

}