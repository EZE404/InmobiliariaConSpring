package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
