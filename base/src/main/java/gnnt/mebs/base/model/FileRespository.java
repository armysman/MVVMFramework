package gnnt.mebs.base.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gnnt.mebs.base.util.FileUtils;
import gnnt.mebs.base.util.Preconditions;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/*******************************************************************
 * FileRespository.java  2019/3/6
 * <P>
 * 文件缓存管理类<br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class FileRespository<Data> extends SimpleRespository<Data> {

    /**
     * 默认的编码方式
     */
    public static final String DEFAULT_ENCODE = "utf-8";

    /**
     * 缓存文件夹路径
     */
    public static final String CACHE_DIR = "FileRespository";

    /**
     * 缓存文件
     */
    private File mCacheFile;
    /**
     * 编码方式
     */
    private String mEncode;

    /**
     * json解析类
     */
    private Gson mGson;

    /**
     * 使用Android CacheDir作为路径
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public FileRespository(@NonNull Context context, String fileName) {
        this(getCacheDir(context), fileName);
    }

    /**
     * 使用指定路径和文件名作为缓存
     *
     * @param path     路径
     * @param fileName 文件名
     */
    public FileRespository(@NonNull String path, @NonNull String fileName) {
        this(path, fileName, DEFAULT_ENCODE);
    }

    /**
     * 使用指定路径、文件名和编码方式作为缓存
     *
     * @param path     路径
     * @param fileName 文件名
     * @param encode   编码
     */
    public FileRespository(@NonNull String path, @NonNull String fileName, @Nullable String encode) {
        Preconditions.checkNotNull(path, "保存路径不能为空");
        Preconditions.checkNotNull(fileName, "保存文件名称不能为空");
        this.mCacheFile = new File(path, fileName);
        this.mEncode = encode;
        // 如果编码为空，则使用默认编码
        if (TextUtils.isEmpty(this.mEncode)) {
            this.mEncode = DEFAULT_ENCODE;
        }
        mGson = new Gson();
    }

    @Override
    public Single<Data> loadLocalData() {

        return Single.create(new SingleOnSubscribe<Data>() {
            @Override
            public void subscribe(SingleEmitter<Data> emitter) throws Exception {
                String jsonString = FileUtils.readFileContent(mCacheFile, mEncode);
                Data data = null;
                if (!TextUtils.isEmpty(jsonString)) {
                    data = mGson.fromJson(jsonString, getSuperclassTypeParameter(getClass()));
                }
                emitter.onSuccess(data);
            }
        });
    }

    @Override
    public void saveLocalData(Data data) {
        String jsonString = "";
        if (data != null) {
            jsonString = mGson.toJson(data);
        }
        FileUtils.writeFileContent(mCacheFile, jsonString, mEncode);
    }

    /**
     * 获取缓存路径
     *
     * @param context 上下文
     * @return 缓存路径
     */
    static String getCacheDir(Context context) {
        File file = new File(context.getCacheDir(), CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取泛型类型
     *
     * @param subclass 子类
     * @return 返回父类泛型类型
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


}
