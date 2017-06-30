package data;

import cn.bmob.v3.BmobObject;

/**
 * Created by Min on 2017-06-30.
 */

public class Collection extends BmobObject {
    private String url;
    private String title;
    private boolean IsCollection;
    private String BMOB_ID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBMOB_ID() {
        return BMOB_ID;
    }

    public void setBMOB_ID(String BMOB_ID) {
        this.BMOB_ID = BMOB_ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCollection() {
        return IsCollection;
    }

    public void setCollection(boolean collection) {
        IsCollection = collection;
    }
}
