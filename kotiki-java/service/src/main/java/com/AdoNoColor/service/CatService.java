package com.AdoNoColor.service;

import com.AdoNoColor.domain.entity.*;
import com.AdoNoColor.domain.entity.model.CatModel;
import com.AdoNoColor.repository.CatRepository;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.service.exceptions.CatAlreadyExistsException;
import com.AdoNoColor.service.exceptions.CatNotFoundException;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import com.AdoNoColor.service.exceptions.UserRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private UserEntityRepository userRepo;

    public Cat createCat(Cat cat, Integer owner_id, String username) throws CatAlreadyExistsException,
            UserRestrictionException {
        UserEntity user = userRepo.findByUsername(username);
        if (user.getRole() == Role.USER.name() && user.getOwner().getId() != owner_id){
            throw new UserRestrictionException("Forbidden for that type of user!");
        }

        Owner owner = ownerRepo.findById(owner_id).get();
        cat.setOwner(owner);
        return catRepo.save(cat);
    }

    public CatModel getCat(Integer id) throws CatNotFoundException {
        if (catRepo.findById(id) == null) {
            throw new CatNotFoundException("Cat was not found!");
        }

        return CatModel.toModel(catRepo.findById(id).get());
    }

    public Integer deleteCat(Integer id, String username) throws CatNotFoundException, UserRestrictionException {
        Cat cat = catRepo.findById(id).get();
        UserEntity user = userRepo.findByUsername(username);
        if (user.getRole() == Role.USER.name() && user.getOwner().getId() != cat.getOwner().getId()){
            throw new UserRestrictionException("Forbidden for that type of user!");
        }

        if (cat == null) {
            throw new CatNotFoundException("Cat was not found!");
        }

        catRepo.delete(cat);
        return id;
    }

    public Cat updateCatName(Integer id, String name, String username) throws CatNotFoundException,
            UserRestrictionException {
        Cat cat = catRepo.findById(id).get();
        UserEntity user = userRepo.findByUsername(username);
        if (user.getRole() == Role.USER.name() && user.getOwner().getId() != cat.getOwner().getId()){
            throw new UserRestrictionException("Forbidden for that type of user!");
        }

        if (cat == null) {
            throw new CatNotFoundException("User was not found!");
        }

        cat.setName(name);

        return catRepo.save(cat);
    }

    public Cat makeCatsFriends(Integer cat1_id, Integer cat2_id){
        Cat cat1 = catRepo.findById(cat1_id).get();
        Cat cat2 = catRepo.findById(cat2_id).get();
        cat1.AddFriend(cat2);
        cat2.AddFriend(cat1);
        catRepo.save(cat2);
        return catRepo.save(cat1);
    }

    public void deleteCatsFriendship(Integer cat1_id, Integer cat2_id) {
        Cat cat1 = catRepo.findById(cat1_id).get();
        Cat cat2 = catRepo.findById(cat2_id).get();
        cat1.DeleteFriend(cat2);
        cat2.DeleteFriend(cat1);
        catRepo.save(cat1);
        catRepo.save(cat2);
    }

    public Collection<CatModel> getAllCatsByColor(Color color) {
        return catRepo.getByColor(color).stream().map(CatModel::toModel).collect(Collectors.toList());
    }

    public Collection<CatModel> getAllCatsByBreed(Breed breed) {
        return catRepo.getByBreed(breed).stream().map(CatModel::toModel).collect(Collectors.toList());
    }
}
