package io.bootify.my_app.service;

import io.bootify.my_app.domain.Owner;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.model.OwnerDTO;
import io.bootify.my_app.repos.OwnerRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.WebUtils;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PropertyRepository propertyRepository;

    public OwnerService(final OwnerRepository ownerRepository,
            final PropertyRepository propertyRepository) {
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<OwnerDTO> findAll() {
        final List<Owner> owners = ownerRepository.findAll(Sort.by("id"));
        return owners.stream()
                .map(owner -> mapToDTO(owner))
                .toList();
    }

    public OwnerDTO get(final Long id) {
        return ownerRepository.findById(id)
                .map(owner -> mapToDTO(owner))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OwnerDTO ownerDTO) {
        final Owner owner = new Owner();
        //mapToEntity(ownerDTO, owner);
        return ownerRepository.save(mapToEntity(ownerDTO, owner)).getId();
    }

    public void update(final Long id, final OwnerDTO ownerDTO) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ownerDTO, owner);
        ownerRepository.save(owner);
    }

    public void delete(final Long id) {
        ownerRepository.deleteById(id);
    }

    private OwnerDTO mapToDTO(final Owner owner) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(owner, OwnerDTO.class);
    }

    private Owner mapToEntity(final OwnerDTO ownerDTO, final Owner owner) {
        //TODO: tengo que revisar esta lógica para ver si mapeo lo que recibo o creo una instancia
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ownerDTO, Owner.class);
    }

    public String getReferencedWarning(final Long id) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Property ownerProperty = propertyRepository.findFirstByOwner(owner);
        if (ownerProperty != null) {
            return WebUtils.getMessage("owner.property.owner.referenced", ownerProperty.getId());
        }
        return null;
    }

}
