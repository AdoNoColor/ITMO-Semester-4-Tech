package com.AdoNoColor.service;

import com.AdoNoColor.entity.Owner;
import com.AdoNoColor.entity.Role;
import com.AdoNoColor.entity.UserEntity;
import com.AdoNoColor.entity.model.OwnerModel;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.service.exceptions.OwnerAlreadyExistsException;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import com.AdoNoColor.service.exceptions.UserRestrictionException;
import com.AdoNoColor.tools.KafkaTemplateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OwnerService {
    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @Autowired
    private OwnerRepository ownerRepo;

    @KafkaListener(topics = "searchOwnerById")
    public OwnerModel getById(OwnerModel ownerModel) throws OwnerNotFoundException {
        Owner owner = ownerRepo.findById(ownerModel.getId()).get();

        if (owner == null) {
            throw new OwnerNotFoundException("User was not found!");
        }

        OwnerModel ownerDto = OwnerModel.toModel(owner);
        kafkaTemplateTool.kafkaOwnerTemplate.send("sendOwner", ownerDto);
        return ownerDto;
    }

    @KafkaListener(topics = "updateOwnersName")
    public Owner update(OwnerModel ownerModel) {
        Owner entity = ownerRepo.findById(ownerModel.getId()).get();
        entity.setName(ownerModel.getName());
        return ownerRepo.save(entity);
    }

    public Owner createOwner(Owner owner) throws OwnerAlreadyExistsException {
        if (ownerRepo.findById(owner.getId()) != null) {
            throw new OwnerAlreadyExistsException("Owner has already been registered!");
        }

        return ownerRepo.save(owner);
    }

    @KafkaListener(topics = "deleteOwner")
    public void delete(OwnerModel ownerModel) {
        Owner entity = ownerRepo.findById(ownerModel.getId()).get();
        ownerRepo.delete(entity);
    }
}