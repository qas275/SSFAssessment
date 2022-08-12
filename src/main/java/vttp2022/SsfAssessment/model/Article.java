package vttp2022.SsfAssessment.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Article {
    private String id;
    private String published;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;
    private boolean save = false;

    public String getId() {
        return id;
    }
    public boolean isSave() {
        return save;
    }
    public void setSave(boolean save) {
        this.save = save;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublished() {
        return published;
    }
    public void setPublished(String published) {
        this.published = published;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Value("crypto.apikey")
    String apikey;

    private static final Logger logger = LoggerFactory.getLogger(Article.class);
    public String APIUrl = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    public static List<Article> createJsonList(String JsonBody){
        List<Article> articlesList = new ArrayList();
        try(InputStream is = new ByteArrayInputStream(JsonBody.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject jObj = r.readObject();
            logger.info("asdsa");
            JsonArray jArrayNews = jObj.getJsonArray("Data");
            for (int i=0;i<jArrayNews.size();i++){
                JsonObject currentArticlesAllObj = jArrayNews.getJsonObject(i);
                Article singleArticle = createArticle(currentArticlesAllObj);
                articlesList.add(singleArticle);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return articlesList;
    }

    public static Article createArticle(JsonObject singleArticleObjectJson){
        Article article = new Article();
        article.id = singleArticleObjectJson.getString("id");
        article.published = singleArticleObjectJson.get("published_on").toString();
        article.title = singleArticleObjectJson.getString("title");
        article.url = singleArticleObjectJson.getString("url");
        article.imageurl = singleArticleObjectJson.getString("imageurl");
        article.body = singleArticleObjectJson.getString("body");
        article.tags = singleArticleObjectJson.getString("tags");
        article.categories = singleArticleObjectJson.getString("categories");
        logger.info("ONE ARTICLE INFO >>>> " + article.id + " " + article.imageurl+ " " + article.body + " " + article.published + " " + article.title + " " + article.url + " " + article.tags + " " + article.categories);
        return article;
    }
}
