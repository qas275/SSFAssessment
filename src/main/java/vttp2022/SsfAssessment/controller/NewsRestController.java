package vttp2022.SsfAssessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.SsfAssessment.model.Article;
import vttp2022.SsfAssessment.service.NewsService;

@RestController
@RequestMapping(path = "/")
public class NewsRestController {
    
    @Autowired
    NewsService service;

    @GetMapping(path = "/news/{articleID}")
    public ResponseEntity<String> retrieveID(@PathVariable String articleID){
        Article requestedArticle = service.getbyID(articleID);
        JsonObject responseArticleJson;
        if(null!=requestedArticle){
            JsonObjectBuilder builder = Json.createObjectBuilder().add("id", requestedArticle.getId())
                                                                .add("title", requestedArticle.getTitle())
                                                                .add("body", requestedArticle.getBody())
                                                                .add("published_on", requestedArticle.getPublished())
                                                                .add("url", requestedArticle.getUrl())
                                                                .add("imageurl", requestedArticle.getImageurl())
                                                                .add("tags", requestedArticle.getTags())
                                                                .add("categories", requestedArticle.getCategories());
            responseArticleJson = builder.build();
            return ResponseEntity.ok(responseArticleJson.toString());
        }else{
            String responseMessage = "Cannot find news article "+articleID;
            responseArticleJson = Json.createObjectBuilder().add("error", responseMessage).build();
            return ResponseEntity.status(404).body(responseArticleJson.toString());
        }
    }

}
