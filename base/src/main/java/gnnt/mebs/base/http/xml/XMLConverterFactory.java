package gnnt.mebs.base.http.xml;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/*******************************************************************
 * XMLConverterFactory.java  2019/3/4
 * <P>
 * 解析 XML 协议<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class XMLConverterFactory extends Converter.Factory {

    static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream; charset=GBK");

    @Override
    public Converter<ResponseBody, ApiResponse> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseConverter(type);
    }

    @Override
    public Converter<ApiRequest, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestConverter();
    }

    class RequestConverter implements Converter<ApiRequest, RequestBody> {

        @Override
        public RequestBody convert(ApiRequest value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, value.toXmlString());
        }
    }

    class ResponseConverter implements Converter<ResponseBody, ApiResponse> {

        /**
         * 返回包类型
         */
        private Type type;

        public ResponseConverter(Type type) {
            this.type = type;
        }

        @Override
        public ApiResponse convert(ResponseBody value) throws IOException {

            if (type instanceof Class<?> && ApiResponse.class.isAssignableFrom((Class<?>) type)) {
                try {
                    ApiResponse response = (ApiResponse) ((Class<?>)type).newInstance();
                    response.setValueFromXmlStr(value.string());
                    return response;
                } catch (Exception e) {
                    throw new IOException(e);
                }

            } else {
                throw new IOException("返回包类型不正确");
            }
        }
    }
}
