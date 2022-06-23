package com.AdoNoColor.controller.owner;

import com.AdoNoColor.domain.entity.Owner;
import com.AdoNoColor.domain.entity.UserEntity;
import com.AdoNoColor.domain.entity.model.OwnerModel;
import com.AdoNoColor.service.OwnerService;
import com.AdoNoColor.service.exceptions.OwnerAlreadyExistsException;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import com.AdoNoColor.service.tools.KafkaTemplateTool;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @PreAuthorize("hasAuthority('owners:write')")
    @PostMapping("/delete")
    public ResponseEntity<OwnerModel> Delete(@RequestParam(value = "id") Integer id) {
        OwnerModel owner = new OwnerModel();
        owner.setId(id);

        kafkaTemplateTool.kafkaOwnerTemplate.send("deleteOwner", owner);
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('owners:write')")
    @PostMapping
    public ResponseEntity<OwnerModel> UpdateOwnersName(@RequestBody OwnerModel owner) {
        kafkaTemplateTool.kafkaOwnerTemplate.send("updateOwnersName", owner);

        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('owners:read')")
    @GetMapping
    public ResponseEntity<OwnerModel> SearchOwner(@RequestParam(value = "id") Integer id) {
        OwnerModel owner = new OwnerModel();
        owner.setId(id);
        kafkaTemplateTool.kafkaOwnerTemplate.send("searchOwnerById", owner);
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return ResponseEntity.ok().body(ownerService.getOwnerModel());
    }
}
