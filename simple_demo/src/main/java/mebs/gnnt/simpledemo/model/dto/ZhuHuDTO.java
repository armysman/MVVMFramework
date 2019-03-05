package mebs.gnnt.simpledemo.model.dto;

/*******************************************************************
 * ZhuHuDTO.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface ZhuHuDTO {

    class Response {

        /**
         * 专栏跟随人数
         */
        public int followers;

        /**
         * 专栏所属人
         */
        public String description;

        /**
         * 作者
         */
        public Author author;
    }

    class Author {

        /**
         * 专栏所属人姓名
         */
        public String name;

    }
}
