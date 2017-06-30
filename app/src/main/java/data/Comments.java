package data;

import cn.bmob.v3.BmobObject;

/**
 * Created by Min on 2017/5/31.
 */

public class Comments extends BmobObject {
    private String head;
    private String comment_content;
    private String url;
    private boolean IsCollection = false;

    public Comments(String comment_content){
        this.comment_content = comment_content;
    }

    public Comments(String head,String comment_content){
        this.head = head;
        this.comment_content = comment_content;
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
        this.IsCollection = collection;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
