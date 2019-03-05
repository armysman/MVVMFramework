package gnnt.mebs.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*******************************************************************
 * FileUtils.java  2018/12/11
 * <P>
 * 文件操作类<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class FileUtils {

    /**
     * 一次性写入文件内容
     *
     * @param file    文件
     * @param content 内容
     * @param charset 编码
     */
    public static void writeFileContent(File file, String content, String charset) {
        if (content == null) {
            content = "";
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes(charset));
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(outputStream);
        }
    }

    /**
     * 一次性读取文件内容
     *
     * @param file    文件
     * @param charset 编码
     * @throws Exception 异常
     */
    public static String readFileContent(File file, String charset) {
        FileInputStream inputStream = null;
        String result = "";
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            result = new String(fileContent, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
        }
        return result;


    }

    /**
     * 关闭流
     *
     * @param closeable 流
     */
    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
