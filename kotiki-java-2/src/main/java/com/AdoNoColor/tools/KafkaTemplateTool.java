package com.AdoNoColor.tools;

import com.AdoNoColor.entity.model.CatModel;
import com.AdoNoColor.entity.model.OwnerModel;
import com.AdoNoColor.entity.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class KafkaTemplateTool {
    @Autowired
    public KafkaTemplate<Long, UserModel> kafkaUserTemplate;

    @Autowired
    public KafkaTemplate<Long, List<UserModel>> kafkaUsersTemplate;

    @Autowired
    public KafkaTemplate<Long, CatModel> kafkaKotikTemplate;

    @Autowired
    public KafkaTemplate<Long, List<CatModel>> kafkaKotikiTemplate;

    @Autowired
    public KafkaTemplate<Long, OwnerModel> kafkaOwnerTemplate;

    @Autowired
    public KafkaTemplate<Long, List<OwnerModel>> kafkaOwnersTemplate;

    @Autowired
    public KafkaTemplate<Long, String> KafkaStringTemplate;
}
