package gnnt.mebs.base;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        TestParse<List<User>> parse = new TestParse<List<User>>(){};
        Object object = parse.parse("[{'name':'周鹏','id':1992}]");
        System.out.print(object);
    }

    public static class TestParse<T> {

        protected T parse(String s) {
            Type type = getSuperclassTypeParameter(getClass());
            return new Gson().fromJson(s, type);
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
    }


    public static class User {
        public String name;
        public int id;
    }

    public static class DataContent<T> {

        public Type getType() {
            ParameterizedType genType = (ParameterizedType) getClass().getGenericSuperclass();

            Type[] actualTypeArguments = genType.getActualTypeArguments();

            return actualTypeArguments[0];
        }


    }

    /*
     * 获取泛型类Class对象，不是泛型类则返回null
     */
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entitiClass;
    }
}