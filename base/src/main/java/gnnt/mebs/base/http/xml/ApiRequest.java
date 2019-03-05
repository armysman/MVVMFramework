package gnnt.mebs.base.http.xml;


import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;


import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.lang.reflect.Field;

import gnnt.mebs.base.http.HttpConfig;

/*******************************************************************
 * ApiRequest.java  2018/11/29
 * <P>
 * UserApi 请求类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class ApiRequest {

    // 写日志标示
    private String tag = this.getClass().getName();

    /**
     * 设置协议名称,＊注＊ 必须设置<br/>
     * <br/>
     */
    protected abstract String getProtocolName();

    /**
     * 转换为协议规定的xml格式字符串，包含空值标签 <br/>
     * <br/>
     *
     * @return xml文档字符串
     */
    public String toXmlString() {
        return toXmlString(true);
    }

    /**
     * 转换为协议规定的xml格式字符串 <br/>
     * <br/>
     *
     * @param includeNullTag 是否包含空标签,M3服务器需要包含，M6服务器 不用包含
     * @return xml文档字符串
     */
    public String toXmlString(boolean includeNullTag) {
        if (TextUtils.isEmpty(getProtocolName())) {
            throw new IllegalArgumentException("请求包的协议名称不能为空！");
        }
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            // <?xml version="1.0″ encoding="UTF-8″ standalone="yes"?>
            serializer.startDocument("utf-8", true);

            serializer.startTag("", "MEBS_MOBILE");
            serializer.startTag("", "REQ");
            serializer.attribute("", "name", getProtocolName());
            Field fields[] = this.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                // 反射字段声明类型为private的字段
                if (!fields[i].isAccessible()) {
                    fields[i].setAccessible(true);
                }
                Object value = fields[i].get(this);
                if (!isEmptyValue(value)) {  // 如果属性有值
                    serializer.startTag("", getFieldName(fields[i]));
                    serializer.text(value.toString());
                    serializer.endTag("", getFieldName(fields[i]));
                } else if (includeNullTag) { // 如果属性无值，且需要包含空标签
                    serializer.startTag("", getFieldName(fields[i]));
                    serializer.text("");
                    serializer.endTag("", getFieldName(fields[i]));
                }
            }
            // 此处添加一个空字符串，避免产生<REQ />标签
            serializer.text("");
            serializer.endTag("", "REQ");
            serializer.endTag("", "MEBS_MOBILE");

            serializer.endDocument();
        } catch (Exception e) {
            Log.e(tag, "将请求包转换为xml字符串时出现问题，问题原因：" + e.getMessage());
        }
        //为了xml解析时和服务器保持一致，上面的utf-8是为了反射时不产生乱码，这个是为了服务器解析正确
        return writer.toString().replaceAll("utf-8", HttpConfig.HOST_ENCODE);
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
     * 对象是否为空
     *
     * @param value 需要判断的对象
     * @return
     */
    private boolean isEmptyValue(Object value) {
        if (value == null || TextUtils.isEmpty(value.toString())) {
            return true;
        }
        return false;
    }

}
