package gnnt.mebs.base.http.xml;

import android.text.TextUtils;
import android.util.Log;

import gnnt.mebs.base.http.HttpConfig;
import gnnt.mebs.base.http.HttpException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*******************************************************************
 * ApiResponse.java  2018/11/29
 * <P>
 * 返回包<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class ApiResponse {

    /**
     * 返回包message子弹
     */
    static final String REP_MESSAGE = "MESSAGE";
    /**
     * 返回包返回码字段
     */
    static final String REP_RETCODE = "RETCODE";
    /**
     * 返回包参数字段
     */
    static final String REP_ARGS = "ARGS";

    // 写日志标示
    private String tag = this.getClass().getName();

    /**
     * 将Xml类型的字符串赋值给相应的字段<br/>
     * <br/>
     *
     * @param xmlStr xml格式的字符串
     */
    public void setValueFromXmlStr(String xmlStr) {
        if (xmlStr == null || xmlStr.length() == 0) {
            throw new IllegalArgumentException("xml格式的字符串不能为空！");
        }

        DocumentBuilderFactory docBuilderFactory = null;
        DocumentBuilder docBuilder = null;
        Document document = null;
        InputStream inputStream = null;
        try {
            // 获取数据流
            inputStream = new ByteArrayInputStream(xmlStr.getBytes(Charset
                    .forName(HttpConfig.HOST_ENCODE)));
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            // 将输入流解析
            document = docBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            // 获取REP节点
            NodeList repNodeList = root.getElementsByTagName("REP");

            if (repNodeList == null || repNodeList.getLength() != 1) {
                throw new HttpException("没有REP节点，或者REP节点多于1个！");
            }

            Element element = (Element) repNodeList.item(0);
            // 获取属性名也就是协议中的协议名称
            String protocolName = element.getAttribute("name");
            if (TextUtils.isEmpty(protocolName)) {
                throw new HttpException("网络繁忙，请稍等重试!");
            }
            //TODO
            // 获取message节点
            NodeList argsNodeList = root.getElementsByTagName(REP_MESSAGE);
            if (argsNodeList != null && argsNodeList.getLength() > 0) {//message为空走以前的逻辑代码
                setValue(this, element);
            } else {//新协议走
                setValueNew(this, element);
            }
        } catch (HttpException e) {
            Log.e(tag, e.getMessage());
            setValueByReflect("-1234567", e.getMessage());
        } catch (Exception e) {
            Log.e(tag,
                    "解析xml字符串失败,字符串内容:" + xmlStr + "，失败原因：" + e.getMessage());
            setValueByReflect("-7654321",
                    "解析xml字符串失败!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(tag, "解析xml字符串关闭流失败，失败原因：" + e.getMessage());
                }
                inputStream = null;
            }

            document = null;
            docBuilder = null;
            docBuilderFactory = null;
        }
    }

    /**
     * 当发生异常时通过反射给返回码和返回内容赋值，防止给客户端返回空的内容 <br/>
     *
     * @param retCode 返回码
     * @param message 错误信息
     */
    public void setValueByReflect(String retCode, String message) {
        // 当发生异常时反射给结果赋值，因为每一个返回包中都包含RESULT；每一个RESULT中都包含RETCODE，MESSAGE所以可以通过方式进行赋值
        try {
            Field field = (Field) this.getClass().getDeclaredField("RESULT");
            // 将字段属性设置为可访问
            field.setAccessible(true);
            Object result = field.getType().getConstructor(this.getClass())
                    .newInstance(this);
            Field retCodeField = result.getClass().getDeclaredField("RETCODE");
            // 将字段属性设置为可访问
            retCodeField.setAccessible(true);
            Field retMsgField = result.getClass().getDeclaredField("MESSAGE");
            // 将字段属性设置为可访问
            retMsgField.setAccessible(true);
            retCodeField.set(result, retCode);
            retMsgField.set(result, message);

            field.set(this, result);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通过反射将xml中的值赋值到相应的对象<br/>
     * <br/>
     *
     * @param obj     待赋值的对象
     * @param element xml元素
     */
    private void setValue(Object obj, Element element) {
        try {
            // 遍历所有字段
            Field fields[] = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 反射私有字段
                if (!field.isAccessible()) {
                    // 将字段属性设置为可访问
                    field.setAccessible(true);
                }
                // 如果字段是字符串类型则直接赋值
                if (field.getType().getName().equals("java.lang.String")) {
                    String fieldName = getFieldName(field);
                    NodeList nodeList = element
                            .getElementsByTagName(fieldName);
                    if (nodeList != null && nodeList.getLength() > 0) {
                        Log.d(tag, "fieldName=" + fieldName
                                + " content="
                                + nodeList.item(0).getTextContent());
                        // 赋值
                        field.set(obj, nodeList.item(0).getTextContent());
                    }

                }
                // 如果是字段是List类型
                else if (List.class.isAssignableFrom(field.getType())) {
                    // 定义List的基础类型，也即List对应的泛型类型
                    Class<?> baseType = null;
                    // 为了确保安全转换，使用instanceof
                    if (field.getGenericType() instanceof ParameterizedType) {
                        // 执行强制类型转换
                        ParameterizedType parameterizedType = (ParameterizedType) field
                                .getGenericType();

                        // 获取泛型类型的泛型参数
                        baseType = (Class<?>) parameterizedType
                                .getActualTypeArguments()[0];
                    }

                    // 实例化对象列表
                    Object list = field.getType().newInstance();
                    // 字段名称
                    String fieldName = getFieldName(field);
                    // 获取字段对应的节点列表
                    NodeList nodeList = element
                            .getElementsByTagName(fieldName);
                    if (nodeList == null) {
                        continue;
                    }
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        // 初始化基础类型对象
                        Object baseObj = null;
                        // 如果是静态类则直接实例化
                        if (Modifier.isStatic(baseType.getModifiers()))// 静态内部类的处理
                            baseObj = baseType.getConstructor()
                                    .newInstance();
                        else
                            // 实例内部类的处理
                            baseObj = baseType.getConstructor(
                                    this.getClass()).newInstance(this);
                        // 递归调用
                        setValue(baseObj, (Element) nodeList.item(i));
                        // 获取List的添加方法，通过反射的方式添加元素
                        Method method = field.getType().getMethod("add",
                                Object.class);
                        // 添加元素
                        method.invoke(list, baseObj);
                    }

                    // 反射赋值
                    field.set(obj, list);
                } else if (field.getType().isMemberClass()) {
                    // 字段名称
                    String fieldName = getFieldName(field);
                    // 获取字段名称对应的节点
                    NodeList nodeList = element
                            .getElementsByTagName(fieldName);
                    if (nodeList == null || nodeList.getLength() != 1) {
                        continue;
                    }

                    // 子对象
                    Object childObj = null;
                    // 字段类
                    Class<?> clazz = field.getType();

                    // 如果是静态类则直接实例化
                    if (Modifier.isStatic(clazz.getModifiers()))// 静态内部类的处理
                        childObj = clazz.getConstructor().newInstance();
                    else
                        // 实例内部类的处理
                        childObj = clazz.getConstructor(this.getClass())
                                .newInstance(this);

                    // 递归赋值
                    setValue(childObj, (Element) nodeList.item(0));
                    // 反射赋值
                    field.set(obj, childObj);
                } else if (field.getType() == this.getClass()) {
                    // 由于类成员都是内部类所以每个类成员中都包含一个this 的成员
                    continue;
                } else {
                    Log.w("不支持的反射类型：", field.getType().getName()
                            + ";字段名称=" + getFieldName(field));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "反射赋值失败，失败原因：" + e.getMessage());
        }
    }

    /**
     * 通过反射将xml中的值赋值到相应的对象<br/>
     * <br/>
     *
     * @param obj     待赋值的对象
     * @param element xml元素
     */
    private void setValueNew(Object obj, Element element) {
        try {
            // 遍历所有字段
            Field fields[] = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 只反射私有字段
                if (!field.isAccessible()) {
                    // 将字段属性设置为可访问
                    field.setAccessible(true);
                    // 如果字段是字符串类型则直接赋值
                    if (field.getType().getName().equals("java.lang.String")) {
                        String fieldName = getFieldName(field);
                        NodeList nodeList = element
                                .getElementsByTagName(fieldName);
                        if (nodeList == null || nodeList.getLength() == 0 && REP_MESSAGE.equals(fieldName)) {
                            //判断是否是message,如果是则去给message赋值
                            setMessageByArgs(field, obj, element);
                        } else if (nodeList != null && nodeList.getLength() > 0) {
                            Log.d(tag, "fieldName=" + fieldName
                                    + " content="
                                    + nodeList.item(0).getTextContent());
                            // 赋值
                            field.set(obj, nodeList.item(0).getTextContent());
                        }

                    }
                    // 如果是字段是List类型
                    else if (List.class.isAssignableFrom(field.getType())) {
                        // 定义List的基础类型，也即List对应的泛型类型
                        Class<?> baseType = null;
                        // 为了确保安全转换，使用instanceof
                        if (field.getGenericType() instanceof ParameterizedType) {
                            // 执行强制类型转换
                            ParameterizedType parameterizedType = (ParameterizedType) field
                                    .getGenericType();

                            // 获取泛型类型的泛型参数
                            baseType = (Class<?>) parameterizedType
                                    .getActualTypeArguments()[0];
                        }

                        // 实例化对象列表
                        Object list = field.getType().newInstance();
                        // 字段名称
                        String fieldName = getFieldName(field);
                        // 获取字段对应的节点列表
                        NodeList nodeList = element
                                .getElementsByTagName(fieldName);
                        if (nodeList == null) {
                            continue;
                        }
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            // 初始化基础类型对象
                            Object baseObj = null;
                            // 如果是静态类则直接实例化
                            if (Modifier.isStatic(baseType.getModifiers()))// 静态内部类的处理
                                baseObj = baseType.getConstructor()
                                        .newInstance();
                            else
                                // 实例内部类的处理
                                baseObj = baseType.getConstructor(
                                        this.getClass()).newInstance(this);
                            // 递归调用
                            setValueNew(baseObj, (Element) nodeList.item(i));
                            // 获取List的添加方法，通过反射的方式添加元素
                            Method method = field.getType().getMethod("add",
                                    Object.class);
                            // 添加元素
                            method.invoke(list, baseObj);
                        }

                        // 反射赋值
                        field.set(obj, list);
                    } else if (field.getType().isMemberClass()) {
                        // 字段名称
                        String fieldName = getFieldName(field);
                        // 获取字段名称对应的节点
                        NodeList nodeList = element
                                .getElementsByTagName(fieldName);
                        if (nodeList == null || nodeList.getLength() != 1) {
                            continue;
                        }

                        // 子对象
                        Object childObj = null;
                        // 字段类
                        Class<?> clazz = field.getType();

                        // 如果是静态类则直接实例化
                        if (Modifier.isStatic(clazz.getModifiers()))// 静态内部类的处理
                            childObj = clazz.getConstructor().newInstance();
                        else
                            // 实例内部类的处理
                            childObj = clazz.getConstructor(this.getClass())
                                    .newInstance(this);

                        // 递归赋值
                        setValueNew(childObj, (Element) nodeList.item(0));
                        // 反射赋值
                        field.set(obj, childObj);
                    } else if (field.getType() == this.getClass()) {
                        // 由于类成员都是内部类所以每个类成员中都包含一个this 的成员
                        continue;
                    } else {
                        Log.w("不支持的反射类型：", field.getType().getName()
                                + ";字段名称=" + getFieldName(field));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "反射赋值失败，失败原因：" + e.getMessage());
        }
    }

    /**
     * 获取字段名，可以根据{@link XMLSerializedName}注解进行反射
     *
     * @param field 字段
     * @return 名称
     */
    private String getFieldName(Field field) {
        XMLSerializedName serializedName = field.getAnnotation(XMLSerializedName.class);
        if (serializedName != null && !TextUtils.isEmpty(serializedName.value())) {
            return serializedName.value();
        }
        return field.getName();
    }

    /**
     * 通过args设置Message
     *
     * @param obj
     * @param element
     */
    private void setMessageByArgs(Field field, Object obj, Element element) {
        try {
            field.setAccessible(true);
            NodeList nodeList = element.getElementsByTagName(REP_RETCODE);
            NodeList argsList = element.getElementsByTagName(REP_ARGS);
            String retCode = nodeList.item(0).getTextContent();

            if (retCode.equals("0")) {//等于0返回码，不管message
                return;
            }

            String message = "";
            // TODO 此处暂时没有处理返回码情况
//            message = HttpCommunicateMemoryData.getInstance().getMessageMap(retCode) ;

            //TODO 添加默认message
            if (message == null || message.trim().length() == 0) {
                message = "错误码：" + retCode;
            } else {
                if (argsList != null && argsList.getLength() > 0) {
                    String args = argsList.item(0).getTextContent();
                    if (args != null && args.trim().length() >= 0) {
                        if (args.contains("|")) { //如果包含个参数，则拆分
                            Object[] o = args.split("\\|");
                            message = String.format(message, o);
                        } else {//单个参数，直接赋值
                            message = String.format(message, args);
                        }
                    }
                }
            }
            field.set(obj, message);
        } catch (Exception e) {
            if (e != null) {
                Log.e(tag, e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
