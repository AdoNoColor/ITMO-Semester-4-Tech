package com.AdoNoColor.service;

import com.AdoNoColor.domain.entity.Owner;
import com.AdoNoColor.domain.entity.model.OwnerModel;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.service.exceptions.OwnerAlreadyExistsException;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepo;

    public Owner createOwner(Owner owner) throws OwnerAlreadyExistsException {
        if (ownerRepo.findById(owner.getId()) != null) {
            throw new OwnerAlreadyExistsException("Owner has already been registered!");
        }

        return ownerRepo.save(owner);
    }

    public Integer deleteOwner(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepo.findById(id).get();

        if (owner == null) {
            throw new OwnerNotFoundException("User was not found!");
        }

        ownerRepo.delete(owner);
        return id;
    }

    public OwnerModel getOwnerById(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepo.findById(id).get();

        if (owner == null) {
            throw new OwnerNotFoundException("User was not found!");
        }

        return OwnerModel.toModel(owner);
    }

    public Owner updateOwnerName(Integer id, String name) throws OwnerNotFoundException {
        Owner owner = ownerRepo.findById(id).get();

        if (owner == null) {
            throw new OwnerNotFoundException("User was not found!");
        }

        owner.setName(name);

        return ownerRepo.save(owner);
    }
}