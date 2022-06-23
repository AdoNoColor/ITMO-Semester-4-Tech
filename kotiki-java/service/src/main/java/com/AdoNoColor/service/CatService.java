package com.AdoNoColor.service;

import com.AdoNoColor.domain.entity.*;
import com.AdoNoColor.domain.entity.model.CatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {

    private CatModel catModel;
    private List<CatModel> catsModel;


    @Autowired
    public CatService() {
    }


    @KafkaListener(topics = "sendCat")
    public void getKotik(CatModel catModel) {
        this.catModel = catModel;
    }

    @KafkaListener(topics = "sendCat")
    public void getKotiki(List<CatModel> catsModel) {
        this.catsModel = catsModel;
    }

    public CatModel getCatModel() {
        return catModel;
    }

    public void setCatModel(CatModel catModel) {
        this.catModel = catModel;
    }

    public List<CatModel> getCatsModel() {
        return catsModel;
    }

    public void setCatsModel(List<CatModel> catsModel) {
        this.catsModel = catsModel;
    }
}
