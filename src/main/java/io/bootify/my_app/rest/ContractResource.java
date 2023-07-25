package io.bootify.my_app.rest;

import io.bootify.my_app.model.ContractDTO;
import io.bootify.my_app.service.ContractService;
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
@RequestMapping(value = "/api/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractResource {

    private final ContractService contractService;

    public ContractResource(final ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(contractService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createContract(@RequestBody @Valid final ContractDTO contractDTO) {
        final Long createdId = contractService.create(contractDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateContract(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ContractDTO contractDTO) {
        contractService.update(id, contractDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContract(@PathVariable(name = "id") final Long id) {
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
