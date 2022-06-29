package com.AdoNoColor.controller.cat;

import com.AdoNoColor.domain.entity.*;
import com.AdoNoColor.service.CatService;
import com.AdoNoColor.service.UserEntityService;
import com.AdoNoColor.service.exceptions.CatAlreadyExistsException;
import com.AdoNoColor.service.exceptions.CatNotFoundException;
import com.AdoNoColor.service.exceptions.UserRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private UserEntityService userService;

    @Autowired
    private CatService catService;

    @PreAuthorize("hasAuthority('cats:write')")
    @PostMapping
    public ResponseEntity createCat(@RequestBody Cat cat,
                                    @RequestParam Integer owner_id,
                                    @AuthenticationPrincipal UserDetails user) {
        try {
            catService.createCat(cat, owner_id, user.getUsername());
            return ResponseEntity.ok("Cat was successfully added!");
        } catch (CatAlreadyExistsException | UserRestrictionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('cats:write')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCat(@PathVariable Integer id,
                                    @AuthenticationPrincipal UserDetails user) {
        try {
            catService.deleteCat(id, user.getUsername());
            return ResponseEntity.ok("Cat was successfully deleted!");
        } catch (CatNotFoundException | UserRestrictionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('cats:write')")
    @PutMapping
    public ResponseEntity updateCatName(@RequestParam Integer id,
                                        @RequestParam String name,
                                        @AuthenticationPrincipal UserEntity user) {
        try {
            catService.updateCatName(id, name, user.getUsername());
            return ResponseEntity.ok("Cat was successfully added!");
        } catch (CatNotFoundException | UserRestrictionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasAuthority('cats:read')")
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

    @PreAuthorize("hasAuthority('cats:read')")
    @GetMapping("/allbycolor/{color}")
    public ResponseEntity getAllCatsByColor(@PathVariable Color color) {
        try {
            return ResponseEntity.ok(catService.getAllCatsByColor(color));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('cats:read')")
    @GetMapping("/allbybreed/{breed}")
    public ResponseEntity getAllCatsByColor(@PathVariable Breed breed) {
        try {
            return ResponseEntity.ok(catService.getAllCatsByBreed(breed));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
