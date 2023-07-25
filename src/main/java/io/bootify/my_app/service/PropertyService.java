package io.bootify.my_app.service;

import io.bootify.my_app.domain.Contract;
import io.bootify.my_app.domain.Owner;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.model.PropertyDTO;
import io.bootify.my_app.repos.ContractRepository;
import io.bootify.my_app.repos.OwnerRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final OwnerRepository ownerRepository;
    private final ContractRepository contractRepository;

    public PropertyService(final PropertyRepository propertyRepository,
            final OwnerRepository ownerRepository, final ContractRepository contractRepository) {
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
        this.contractRepository = contractRepository;
    }

    public List<PropertyDTO> findAll() {
        final List<Property> propertys = propertyRepository.findAll(Sort.by("id"));
        return propertys.stream()
                .map(property -> mapToDTO(property, new PropertyDTO()))
                .toList();
    }

    public PropertyDTO get(final Long id) {
        return propertyRepository.findById(id)
                .map(property -> mapToDTO(property, new PropertyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PropertyDTO propertyDTO) {
        final Property property = new Property();
        mapToEntity(propertyDTO, property);
        return propertyRepository.save(property).getId();
    }

    public void update(final Long id, final PropertyDTO propertyDTO) {
        final Property property = propertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(propertyDTO, property);
        propertyRepository.save(property);
    }

    public void delete(final Long id) {
        propertyRepository.deleteById(id);
    }

    private PropertyDTO mapToDTO(final Property property, final PropertyDTO propertyDTO) {
        propertyDTO.setId(property.getId());
        propertyDTO.setType(property.getType());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setRooms(property.getRooms());
        propertyDTO.setActive(property.getActive());
        propertyDTO.setOwner(property.getOwner() == null ? null : property.getOwner().getId());
        return propertyDTO;
    }

    private Property mapToEntity(final PropertyDTO propertyDTO, final Property property) {
        property.setType(propertyDTO.getType());
        property.setDescription(propertyDTO.getDescription());
        property.setAddress(propertyDTO.getAddress());
        property.setRooms(propertyDTO.getRooms());
        property.setActive(propertyDTO.getActive());
        final Owner owner = propertyDTO.getOwner() == null ? null : ownerRepository.findById(propertyDTO.getOwner())
                .orElseThrow(() -> new NotFoundException("owner not found"));
        property.setOwner(owner);
        return property;
    }

    public String getReferencedWarning(final Long id) {
        final Property property = propertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Contract propertyContract = contractRepository.findFirstByProperty(property);
        if (propertyContract != null) {
            return WebUtils.getMessage("property.contract.property.referenced", propertyContract.getId());
        }
        return null;
    }

}
