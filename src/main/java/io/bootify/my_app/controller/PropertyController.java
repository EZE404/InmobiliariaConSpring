package io.bootify.my_app.controller;

import io.bootify.my_app.domain.Owner;
import io.bootify.my_app.model.PropertyDTO;
import io.bootify.my_app.model.PropertyType;
import io.bootify.my_app.repos.OwnerRepository;
import io.bootify.my_app.service.PropertyService;
import io.bootify.my_app.util.CustomCollectors;
import io.bootify.my_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/propertys")
public class PropertyController {

    private final PropertyService propertyService;
    private final OwnerRepository ownerRepository;

    public PropertyController(final PropertyService propertyService,
            final OwnerRepository ownerRepository) {
        this.propertyService = propertyService;
        this.ownerRepository = ownerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeValues", PropertyType.values());
        model.addAttribute("ownerValues", ownerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Owner::getId, Owner::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("propertys", propertyService.findAll());
        return "property/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("property") final PropertyDTO propertyDTO) {
        return "property/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("property") @Valid final PropertyDTO propertyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "property/add";
        }
        propertyService.create(propertyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("property.create.success"));
        return "redirect:/propertys";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("property", propertyService.get(id));
        return "property/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("property") @Valid final PropertyDTO propertyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "property/edit";
        }
        propertyService.update(id, propertyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("property.update.success"));
        return "redirect:/propertys";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = propertyService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            propertyService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("property.delete.success"));
        }
        return "redirect:/propertys";
    }

}
