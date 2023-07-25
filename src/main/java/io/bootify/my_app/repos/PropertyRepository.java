package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Owner;
import io.bootify.my_app.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyRepository extends JpaRepository<Property, Long> {

    Property findFirstByOwner(Owner owner);

}
