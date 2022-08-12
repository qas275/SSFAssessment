package vttp2022.SsfAssessment.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vttp2022.SsfAssessment.model.AllNews;
import vttp2022.SsfAssessment.model.Article;
import vttp2022.SsfAssessment.service.NewsService;

@Controller
public class NewsController {
    @Autowired
    NewsService service;

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    public List<Article> everything;

    @GetMapping(path = "/")
    public String startPage(Model model){
        AllNews Articles = new AllNews();
        Articles.setallarticles(service.getArticles().get());
        everything = Articles.allarticles;//TODO REMOVE!!!
        logger.info("TESTING >>>"+ Articles.getallarticles().get(0).getId());
        model.addAttribute("Articles", Articles);
        return "allNews";
    }

    @PostMapping(path = "/articles")
    public String savePage(@ModelAttribute("Articles") AllNews Articles,Model model){
        AllNews tosave = Articles;
        AllNews everythingA = new AllNews();//TODO REMOVE
        everythingA.allarticles = everything;//TODO REMOVE
        //service.saveArticles(tosave);
        service.saveArticles(everythingA);//TODO REMOVE this saves all articles
        Articles.setallarticles(service.getArticles().get());
        model.addAttribute("Articles", Articles);
        return "allNews";
    }
}
