package com.example.sxm.practice;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private static final String TAG = "Article";
    private String desc;
    private String status;
    private List<detail> detail = new ArrayList<detail>();

    public List<Article.detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Article.detail> detail) {
        this.detail = detail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public class detail{
//        private String title;
//        private String article_url;
//        private String my_abstract;
//        private String article_type;
        private String city;
        private String citiId;
        private String weather;

        public String getCity(){
            return city;
        }
        public void setCity(String city){
            this.city = city;
        }


//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getArticle_url() {
//            return article_url;
//        }
//
//        public void setArticle_url(String article_url) {
//            this.article_url = article_url;
//        }
//
//        public String getMy_abstract() {
//            return my_abstract;
//        }
//
//        public void setMy_abstract(String my_abstract) {
//            this.my_abstract = my_abstract;
//        }
//
//        public String getArticle_type() {
//            return article_type;
//        }
//
//        public void setArticle_type(String article_type) {
//            this.article_type = article_type;
//        }
    }
}
