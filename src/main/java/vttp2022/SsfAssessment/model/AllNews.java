package vttp2022.SsfAssessment.model;

import java.util.List;

public class AllNews {
    public List<Article> allarticles;

    public List<Article> getallarticles() {
        return allarticles;
    }

    public void setallarticles(List<Article> allarticles) {
        this.allarticles = allarticles;
    }
    
}
