package com.AdoNoColor.controller.cat;

import com.AdoNoColor.domain.entity.Breed;
import com.AdoNoColor.domain.entity.Cat;
import com.AdoNoColor.domain.entity.Color;
import com.AdoNoColor.service.CatService;
import com.AdoNoColor.service.exceptions.CatAlreadyExistsException;
import com.AdoNoColor.service.exceptions.CatNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cats")
public class CatController {

    @Autowired
    private CatService catService;

    @PostMapping
    public ResponseEntity createCat(@RequestBody Cat cat, @RequestParam Integer owner_id) {
        try {
            catService.createCat(cat, owner_id);
            return ResponseEntity.ok("Cat was successfully added!");
        } catch (CatAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getCat(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(catService.getCat(id));
        } catch (CatNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCat(@PathVariable Integer id) {
        try {
            catService.deleteCat(id);
            return ResponseEntity.ok("Cat was successfully deleted!");
        } catch (CatNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateCatName(@RequestParam Integer id,
                                        @RequestParam String name){
        try {
            catService.updateCatName(id, name);
            return ResponseEntity.ok("Cat was successfully added!");
        } catch (CatNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cat1_id}+{cat2_id}")
    public ResponseEntity addFriend(@PathVariable Integer cat1_id,
                                    @PathVariable Integer cat2_id){
        try {
            catService.makeCatsFriends(cat1_id, cat2_id);
            return ResponseEntity.ok("Friendship was made between these two!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteFriendship")
    public ResponseEntity deleteFriendship(@RequestParam Integer cat1_id,
                                    @RequestParam Integer cat2_id){
        try {
            catService.deleteCatsFriendship(cat1_id, cat2_id);
            return ResponseEntity.ok("Friendship was deleted between these two!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/allbycolor/{color}")
    public ResponseEntity getAllCatsByColor(@PathVariable Color color) {
        try {
            return ResponseEntity.ok(catService.getAllCatsByColor(color));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/allbybreed/{breed}")
    public ResponseEntity getAllCatsByColor(@PathVariable Breed breed) {
        try {
            return ResponseEntity.ok(catService.getAllCatsByBreed(breed));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
