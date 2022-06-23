package com.AdoNoColor.service;


import com.AdoNoColor.domain.entity.model.OwnerModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class OwnerService {
    private OwnerModel ownerModel;
    private List<OwnerModel> ownersModel;

    public OwnerService() {
    }

    @KafkaListener(topics = "sendOwner")
    public void getOwner(OwnerModel ownerDto) {
        this.ownerModel = ownerModel;
    }

    @KafkaListener(topics = "sendOwners")
    public void getOwners(List<OwnerModel> ownersDto) {
        this.ownersModel = ownersModel;
    }

    public OwnerModel getOwnerModel() {
        return ownerModel;
    }

    public void setOwnerModel(OwnerModel ownerModel) {
        this.ownerModel = ownerModel;
    }

    public List<OwnerModel> getOwnersModel() {
        return ownersModel;
    }

    public void setOwnersModel(List<OwnerModel> ownersModel) {
        this.ownersModel = ownersModel;
    }
}