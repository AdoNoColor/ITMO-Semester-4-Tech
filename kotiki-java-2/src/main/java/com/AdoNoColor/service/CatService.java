package com.AdoNoColor.service;

import com.AdoNoColor.entity.*;
import com.AdoNoColor.entity.model.CatModel;
import com.AdoNoColor.repository.CatRepository;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.service.exceptions.CatAlreadyExistsException;
import com.AdoNoColor.service.exceptions.CatNotFoundException;
import com.AdoNoColor.service.exceptions.UserRestrictionException;
import com.AdoNoColor.tools.KafkaTemplateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @KafkaListener(topics = "createCat")
    public Cat create(CatModel catModel) {
        Cat cat = new Cat(catModel.getName(), catModel.getBreed(), catModel.getColor(), catModel.getDate_of_birth());
        cat.setOwner(ownerRepo.findById(catModel.getOwner()).get());
        return catRepo.save(cat);
    }

    @KafkaListener(topics = "getCatById")
    public CatModel getCat(CatModel cat) {
        Cat cat_res = catRepo.findById(cat.getId()).get();
        CatModel catModel = CatModel.toModel(cat_res);
        kafkaTemplateTool.kafkaKotikTemplate.send("sendCat", catModel);
        return catModel;
    }

    public Integer deleteCat(Integer id, String username) throws CatNotFoundException, UserRestrictionException {
        Cat cat = catRepo.findById(id).get();
        UserEntity user = userRepo.findByUsername(username);
        if (user.getRole() == Role.USER && user.getOwner().getId() != cat.getOwner().getId()){
            throw new UserRestrictionException("Forbidden for that type of user!");
        }

        if (cat == null) {
            throw new CatNotFoundException("Cat was not found!");
        }

        catRepo.delete(cat);
        return id;
    }

    @KafkaListener(topics = "updateCatName")
    public Cat updateName(CatModel entity) {
        Cat kotik = catRepo.findById(entity.getId()).get();
        kotik.setName(entity.getName());
        return catRepo.save(kotik);
    }

    @KafkaListener(topics = "addCatFriend")
    public Cat makeCatsFriends(List<CatModel> cats){
        Integer cat1_id = cats.indexOf(0);
        Integer cat2_id = cats.indexOf(1);
        Cat cat1 = catRepo.findById(cat1_id).get();
        Cat cat2 = catRepo.findById(cat2_id).get();
        cat1.AddFriend(cat2);
        cat2.AddFriend(cat1);
        catRepo.save(cat2);
        return catRepo.save(cat1);
    }

    @KafkaListener(topics = "deleteCatFriend")
    public void deleteCatsFriendship(List<CatModel> cats) {
        Integer cat1_id = cats.indexOf(0);
        Integer cat2_id = cats.indexOf(1);
        Cat cat1 = catRepo.findById(cat1_id).get();
        Cat cat2 = catRepo.findById(cat2_id).get();
        cat1.DeleteFriend(cat2);
        cat2.DeleteFriend(cat1);
        catRepo.save(cat1);
        catRepo.save(cat2);
    }

    @KafkaListener(topics = "getByColor")
    public Collection<Cat> GetCatByColor(CatModel catModel1) {
        Collection<Cat> cats = catRepo.getByColor(catModel1.getColor());
        List<CatModel> catsModel = new ArrayList<>();

        for(Cat cat : cats) {
            CatModel catMod = CatModel.toModel(cat);
            catsModel.add(catMod);
        }

        kafkaTemplateTool.kafkaKotikiTemplate.send("sendCat", catsModel);

        return catRepo.getByColor(catModel1.getColor());
    }

    @KafkaListener(topics = "deleteCat")
    public void delete(CatModel catModel) {
        CatModel catModel1 = getCat(catModel);
        Cat cat = catRepo.findById(catModel1.getId()).get();
        catRepo.delete(cat);
    }

    @KafkaListener(topics = "getByBreed")
    public Collection<Cat> GetCatByBreed(CatModel catModel1) {
        Collection<Cat> cats = catRepo.getByBreed(catModel1.getBreed());
        List<CatModel> catsModel = new ArrayList<>();

        for(Cat cat : cats) {
            CatModel catMod = CatModel.toModel(cat);
            catsModel.add(catMod);
        }

        kafkaTemplateTool.kafkaKotikiTemplate.send("sendCat", catsModel);

        return catRepo.getByColor(catModel1.getColor());
    }
}
