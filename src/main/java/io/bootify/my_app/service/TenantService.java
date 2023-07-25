package io.bootify.my_app.service;

import io.bootify.my_app.domain.Contract;
import io.bootify.my_app.domain.Tenant;
import io.bootify.my_app.model.TenantDTO;
import io.bootify.my_app.repos.ContractRepository;
import io.bootify.my_app.repos.TenantRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final ContractRepository contractRepository;

    public TenantService(final TenantRepository tenantRepository,
            final ContractRepository contractRepository) {
        this.tenantRepository = tenantRepository;
        this.contractRepository = contractRepository;
    }

    public List<TenantDTO> findAll() {
        final List<Tenant> tenants = tenantRepository.findAll(Sort.by("id"));
        return tenants.stream()
                .map(tenant -> mapToDTO(tenant, new TenantDTO()))
                .toList();
    }

    public TenantDTO get(final Long id) {
        return tenantRepository.findById(id)
                .map(tenant -> mapToDTO(tenant, new TenantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TenantDTO tenantDTO) {
        final Tenant tenant = new Tenant();
        mapToEntity(tenantDTO, tenant);
        return tenantRepository.save(tenant).getId();
    }

    public void update(final Long id, final TenantDTO tenantDTO) {
        final Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tenantDTO, tenant);
        tenantRepository.save(tenant);
    }

    public void delete(final Long id) {
        tenantRepository.deleteById(id);
    }

    private TenantDTO mapToDTO(final Tenant tenant, final TenantDTO tenantDTO) {
        tenantDTO.setId(tenant.getId());
        return tenantDTO;
    }

    private Tenant mapToEntity(final TenantDTO tenantDTO, final Tenant tenant) {
        return tenant;
    }

    public String getReferencedWarning(final Long id) {
        final Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Contract tenantContract = contractRepository.findFirstByTenant(tenant);
        if (tenantContract != null) {
            return WebUtils.getMessage("tenant.contract.tenant.referenced", tenantContract.getId());
        }
        return null;
    }

}
