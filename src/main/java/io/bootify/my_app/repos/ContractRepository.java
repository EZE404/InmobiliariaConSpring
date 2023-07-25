package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Contract;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findFirstByProperty(Property property);

    Contract findFirstByTenant(Tenant tenant);

    boolean existsByPropertyId(Long id);

    boolean existsByTenantId(Long id);

}
