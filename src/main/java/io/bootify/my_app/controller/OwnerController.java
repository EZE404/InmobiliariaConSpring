package io.bootify.my_app.controller;

import io.bootify.my_app.model.OwnerDTO;
import io.bootify.my_app.service.OwnerService;
import io.bootify.my_app.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owner/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("owner") final OwnerDTO ownerDTO) {
        return "owner/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("owner") @Valid final OwnerDTO ownerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "owner/add";
        }
        ownerService.create(ownerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("owner.create.success"));
        return "redirect:/owners";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("owner", ownerService.get(id));
        return "owner/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("owner") @Valid final OwnerDTO ownerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "owner/edit";
        }
        ownerService.update(id, ownerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("owner.update.success"));
        return "redirect:/owners";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = ownerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            ownerService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("owner.delete.success"));
        }
        return "redirect:/owners";
    }

}
