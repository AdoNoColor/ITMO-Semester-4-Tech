package com.AdoNoColor.controller.owner;

import com.AdoNoColor.domain.entity.Owner;
import com.AdoNoColor.service.OwnerService;
import com.AdoNoColor.service.exceptions.OwnerAlreadyExistsException;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public ResponseEntity getOwner(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(ownerService.getOwnerById(id));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createOwner(@RequestBody Owner owner) {
        try {
            ownerService.createOwner(owner);
            return ResponseEntity.ok("Owner was successfully added!");
        } catch  (OwnerAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOwner(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ownerService.deleteOwner(id));
        } catch  (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateOwnerName(@RequestParam Integer id,
                                          @RequestParam String name) {
        try {
            return ResponseEntity.ok(ownerService.updateOwnerName(id, name));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
