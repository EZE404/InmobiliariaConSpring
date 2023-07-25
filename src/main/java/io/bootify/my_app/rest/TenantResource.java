package io.bootify.my_app.rest;

import io.bootify.my_app.model.TenantDTO;
import io.bootify.my_app.service.TenantService;
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
@RequestMapping(value = "/api/tenants", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantResource {

    private final TenantService tenantService;

    public TenantResource(final TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        return ResponseEntity.ok(tenantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tenantService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTenant(@RequestBody @Valid final TenantDTO tenantDTO) {
        final Long createdId = tenantService.create(tenantDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTenant(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TenantDTO tenantDTO) {
        tenantService.update(id, tenantDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTenant(@PathVariable(name = "id") final Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
