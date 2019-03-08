package gnnt.mebs.simpledemo.model;

import gnnt.mebs.base.http.LoadException;
import io.reactivex.functions.Function;
import gnnt.mebs.simpledemo.model.dto.Response;

/*******************************************************************
 * UnpackHandler.java  2019/3/7
 * <P>
 * 返回包拆包<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class UnpackHandler<T> implements Function<Response<T>, T> {
    @Override
    public T apply(Response<T> response) throws Exception {
        if (response.code == 200) {
            return response.result;
        }
        throw new LoadException(response.message);
    }
}
