package vttp2022.SsfAssessment.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.SsfAssessment.model.AllNews;
import vttp2022.SsfAssessment.model.Article;

@Service
public class NewsService {
    
    @Value("crypto.apikey")
    String apikey;

    @Autowired
    @Qualifier("news")
    RedisTemplate<String, Article> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(Article.class);
    public String APIUrl = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
    
    public Optional<List<Article>> getArticles(){
        String APIKEY = "1b09baf3bed283c3a20f5cfe591c85af0252ed3ad1935d2fec6648be372408e4";
        List ArticlesList = new LinkedList<>();
        String ArticlesUrl = UriComponentsBuilder.fromUriString(APIUrl)
                                .queryParam("api_key", APIKEY)
                                .toUriString(); //TODO see if can use apikey, api was down just now

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            logger.info("Start to talk to api");
            logger.info(ArticlesUrl);
            resp = template.exchange(APIUrl, HttpMethod.GET, null, String.class, 1);
            logger.info("BODY GOT");
            logger.info(resp.getBody());
            List<Article> response = Article.createJsonList(resp.getBody());
            return Optional.of(response);
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveArticles(AllNews Articles){
        List<Article> toSave = new ArrayList<>();
        for(Article singleArticle:Articles.getallarticles()){
            //if(singleArticle.isSave()){
                toSave.add(singleArticle);
                redisTemplate.opsForValue().set(singleArticle.getId(), singleArticle);
            //}
        }
    }

    public Article getbyID(String articleID){
        Article reqArticle = new Article();
        if(redisTemplate.hasKey(articleID)){
            reqArticle = redisTemplate.opsForValue().get(articleID);
        }else{
            reqArticle = null;
        }
        return reqArticle;
    }
}
