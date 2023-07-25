package io.bootify.my_app.controller;

import io.bootify.my_app.model.TenantDTO;
import io.bootify.my_app.service.TenantService;
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
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(final TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("tenants", tenantService.findAll());
        return "tenant/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("tenant") final TenantDTO tenantDTO) {
        return "tenant/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("tenant") @Valid final TenantDTO tenantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tenant/add";
        }
        tenantService.create(tenantDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("tenant.create.success"));
        return "redirect:/tenants";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("tenant", tenantService.get(id));
        return "tenant/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("tenant") @Valid final TenantDTO tenantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tenant/edit";
        }
        tenantService.update(id, tenantDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("tenant.update.success"));
        return "redirect:/tenants";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = tenantService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            tenantService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("tenant.delete.success"));
        }
        return "redirect:/tenants";
    }

}
