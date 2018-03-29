package main.controller;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import main.service.AdenaReportService;
import main.service.CharacterL2Service;
import main.service.ProductL2Service;
import main.validators.AdenaReportFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdenaReportController {
    private static final String REPORT = "report";
    private static final String ADENA_REPORT_FORM = "adenaReportForm";

    private CharacterL2Service characterL2Service;
    private ProductL2Service productL2Service;
    private AdenaReportService adenaReportService;
    private AdenaReportFormValidator adenaReportFormValidator;

    @ModelAttribute("adenaReport")
    public AdenaReport adenaReport() {
        return new AdenaReport();
    }

    @ModelAttribute("adenaReportForm")
    public AdenaReportForm adenaReportForm() {
        return new AdenaReportForm();
    }

    @InitBinder("adenaReportForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(adenaReportFormValidator);
    }

    @GetMapping("/main")
    public String showReport(Model model) {
        addToModel(model);
        return REPORT;
    }

    @PostMapping(value = "/main/createAdenaReport", params = "addProduct")
    public String addProduct(@ModelAttribute(ADENA_REPORT_FORM) @Valid AdenaReportForm adenaReportForm, BindingResult bindingResult, Model model) {
        if (hasErrors(bindingResult)) {
            model.addAttribute(ADENA_REPORT_FORM, adenaReportForm);
        } else {
            model.addAttribute(ADENA_REPORT_FORM, adenaReportService.addProduct(adenaReportForm));
        }
        addToModel(model);
        return REPORT;
    }

    private boolean hasErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().anyMatch(fieldError -> !fieldError.getField().equals("productAndPriceHolders"));
    }

    @PostMapping(value = "/main/createAdenaReport", params = "saveReport")
    public String saveReport(@ModelAttribute(ADENA_REPORT_FORM) @Valid AdenaReportForm adenaReportForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ADENA_REPORT_FORM, adenaReportForm);
            addToModel(model);
            return REPORT;
        }
        adenaReportService.saveReport(adenaReportForm);
        return "redirect:/main";
    }

    private void addToModel(Model model){
        model.addAttribute("characterList", characterL2Service.findAll());
        model.addAttribute("productL2List", productL2Service.findAll());
        model.addAttribute("adenaReportList", adenaReportService.findAllFetchAll());
    }

    @GetMapping(value = "/main/report/delete/{id}")
    public String deleteReport(@PathVariable("id") Long id) {
        adenaReportService.delete(id);
        return "redirect:/main";
    }

    @Autowired
    public void setCharacterL2Service(CharacterL2Service characterL2Service) {
        this.characterL2Service = characterL2Service;
    }

    @Autowired
    public void setProductL2Service(ProductL2Service productL2Service) {
        this.productL2Service = productL2Service;
    }

    @Autowired
    public void setAdenaReportService(AdenaReportService adenaReportService) {
        this.adenaReportService = adenaReportService;
    }

    @Autowired
    public void setAdenaReportFormValidator(AdenaReportFormValidator adenaReportFormValidator) {
        this.adenaReportFormValidator = adenaReportFormValidator;
    }
}
