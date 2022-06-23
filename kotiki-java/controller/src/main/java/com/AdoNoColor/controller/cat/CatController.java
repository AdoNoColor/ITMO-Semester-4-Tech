package com.AdoNoColor.controller.cat;

import com.AdoNoColor.domain.entity.*;
import com.AdoNoColor.domain.entity.model.CatModel;
import com.AdoNoColor.service.CatService;
import com.AdoNoColor.service.UserEntityService;
import com.AdoNoColor.service.exceptions.CatAlreadyExistsException;
import com.AdoNoColor.service.exceptions.CatNotFoundException;
import com.AdoNoColor.service.exceptions.UserRestrictionException;
import com.AdoNoColor.service.tools.KafkaTemplateTool;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private UserEntityService userService;

    @Autowired
    private CatService catService;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @PreAuthorize("hasAuthority('cats:write')")
    @PostMapping("/create")
    public ResponseEntity<CatModel> createCat(@RequestBody CatModel cat) {
        kafkaTemplateTool.kafkaKotikTemplate.send("createCat", cat);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('cats:write')")
    @PostMapping("/{id}")
    public ResponseEntity<CatModel> deleteCat(@RequestParam(value = "id") Integer id) {
        CatModel cat = new CatModel();
        cat.setId(id);
        kafkaTemplateTool.kafkaKotikTemplate.send("deleteCat", cat);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('cats:write')")
    @PostMapping
    public ResponseEntity<CatModel> UpdateCatName(@RequestBody CatModel catModel) {
        kafkaTemplateTool.kafkaKotikTemplate.send("updateKotik", catModel);
        return ResponseEntity.ok().build();
    }

    @PostMapping(("/{cat1_id}+{cat2_id}"))
    public ResponseEntity<CatModel> AddFriend(@PathVariable Integer cat1_id,
                                              @PathVariable Integer cat2_id) {
        CatModel cat1 = new CatModel();
        CatModel cat2 = new CatModel();
        cat1.setId(cat1_id);
        cat2.setId(cat2_id);
        List<CatModel> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        kafkaTemplateTool.kafkaKotikiTemplate.send("addFriend", cats);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteFriendship")
    public ResponseEntity deleteFriendship(@RequestParam Integer cat1_id,
                                    @RequestParam Integer cat2_id){
        CatModel cat1 = new CatModel();
        CatModel cat2 = new CatModel();
        cat1.setId(cat1_id);
        cat2.setId(cat2_id);
        List<CatModel> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        kafkaTemplateTool.kafkaKotikiTemplate.send("deleteFriend", cats);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('cats:read')")
    @GetMapping
    @SneakyThrows
    public ResponseEntity<CatModel> getCatById(@RequestParam(value = "id") Integer id) {
        CatModel cat = new CatModel();
        cat.setId(id);
        kafkaTemplateTool.kafkaKotikTemplate.send("SearchByIdKotik", cat);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().body(catService.getCatModel());
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('cats:read')")
    @GetMapping("/allbycolor/{color}")
    public ResponseEntity<Collection<CatModel>> GetByColor(@RequestParam(value = "color") Color color) {
        CatModel cat = new CatModel();
        cat.setColor(color);
        kafkaTemplateTool.kafkaKotikTemplate.send("getByColor", cat);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().body(catService.getCatsModel());

    }

    @PreAuthorize("hasAuthority('cats:read')")
    @GetMapping("/allbybreed/{breed}")
    public ResponseEntity<Collection<CatModel>> GetByColor(@RequestParam(value = "breed") Breed breed) {
        CatModel cat = new CatModel();
        cat.setBreed(breed);
        kafkaTemplateTool.kafkaKotikTemplate.send("getByBreed", cat);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().body(catService.getCatsModel());
    }

}
