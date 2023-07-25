package io.bootify.my_app.rest;

import io.bootify.my_app.model.PropertyDTO;
import io.bootify.my_app.service.PropertyService;
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
@RequestMapping(value = "/api/propertys", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropertyResource {

    private final PropertyService propertyService;

    public PropertyResource(final PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllPropertys() {
        return ResponseEntity.ok(propertyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(propertyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProperty(@RequestBody @Valid final PropertyDTO propertyDTO) {
        final Long createdId = propertyService.create(propertyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProperty(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PropertyDTO propertyDTO) {
        propertyService.update(id, propertyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProperty(@PathVariable(name = "id") final Long id) {
        propertyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
