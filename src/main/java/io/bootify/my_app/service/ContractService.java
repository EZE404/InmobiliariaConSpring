package io.bootify.my_app.service;

import io.bootify.my_app.domain.Contract;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.Tenant;
import io.bootify.my_app.model.ContractDTO;
import io.bootify.my_app.repos.ContractRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.repos.TenantRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final TenantRepository tenantRepository;

    public ContractService(final ContractRepository contractRepository,
            final PropertyRepository propertyRepository, final TenantRepository tenantRepository) {
        this.contractRepository = contractRepository;
        this.propertyRepository = propertyRepository;
        this.tenantRepository = tenantRepository;
    }

    public List<ContractDTO> findAll() {
        final List<Contract> contracts = contractRepository.findAll(Sort.by("id"));
        return contracts.stream()
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .toList();
    }

    public ContractDTO get(final Long id) {
        return contractRepository.findById(id)
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ContractDTO contractDTO) {
        final Contract contract = new Contract();
        mapToEntity(contractDTO, contract);
        return contractRepository.save(contract).getId();
    }

    public void update(final Long id, final ContractDTO contractDTO) {
        final Contract contract = contractRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contractDTO, contract);
        contractRepository.save(contract);
    }

    public void delete(final Long id) {
        contractRepository.deleteById(id);
    }

    private ContractDTO mapToDTO(final Contract contract, final ContractDTO contractDTO) {
        contractDTO.setId(contract.getId());
        contractDTO.setDate(contract.getDate());
        contractDTO.setCheckInDate(contract.getCheckInDate());
        contractDTO.setCheckOutDate(contract.getCheckOutDate());
        contractDTO.setValid(contract.getValid());
        contractDTO.setProperty(contract.getProperty() == null ? null : contract.getProperty().getId());
        contractDTO.setTenant(contract.getTenant() == null ? null : contract.getTenant().getId());
        return contractDTO;
    }

    private Contract mapToEntity(final ContractDTO contractDTO, final Contract contract) {
        contract.setDate(contractDTO.getDate());
        contract.setCheckInDate(contractDTO.getCheckInDate());
        contract.setCheckOutDate(contractDTO.getCheckOutDate());
        contract.setValid(contractDTO.getValid());
        final Property property = contractDTO.getProperty() == null ? null : propertyRepository.findById(contractDTO.getProperty())
                .orElseThrow(() -> new NotFoundException("property not found"));
        contract.setProperty(property);
        final Tenant tenant = contractDTO.getTenant() == null ? null : tenantRepository.findById(contractDTO.getTenant())
                .orElseThrow(() -> new NotFoundException("tenant not found"));
        contract.setTenant(tenant);
        return contract;
    }

    public boolean propertyExists(final Long id) {
        return contractRepository.existsByPropertyId(id);
    }

    public boolean tenantExists(final Long id) {
        return contractRepository.existsByTenantId(id);
    }

}
