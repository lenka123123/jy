package sinia.com.baihangeducation.find.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class SearchRecommend {
    public String hot_search ;
    public List<Code> hot_recommend ;

    public class Code {
        public String keyword    ;

    }

}
