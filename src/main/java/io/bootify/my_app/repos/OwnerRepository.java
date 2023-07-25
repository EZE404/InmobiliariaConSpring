package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
