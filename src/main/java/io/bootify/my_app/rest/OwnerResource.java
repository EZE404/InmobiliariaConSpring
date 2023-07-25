package io.bootify.my_app.rest;

import io.bootify.my_app.model.OwnerDTO;
import io.bootify.my_app.service.OwnerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/owners", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerResource {

    private final OwnerService ownerService;

    public OwnerResource(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(ownerService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOwner(@RequestBody @Valid final OwnerDTO ownerDTO) {
        final Long createdId = ownerService.create(ownerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOwner(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OwnerDTO ownerDTO) {
        ownerService.update(id, ownerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOwner(@PathVariable(name = "id") final Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
