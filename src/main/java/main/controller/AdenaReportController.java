package main.controller;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import main.service.AdenaReportService;
import main.service.CharacterL2Service;
import main.service.ProductL2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdenaReportController {

    private CharacterL2Service characterL2Service;
    private ProductL2Service productL2Service;
    private AdenaReportService adenaReportService;

    @ModelAttribute("adenaReport")
    public AdenaReport adenaReport(){
        return new AdenaReport();
    }

    @ModelAttribute("adenaReportForm")
    public AdenaReportForm adenaReportForm(){
        return new AdenaReportForm();
    }

    @GetMapping("/main")
    public String showReport(Model model){
        model.addAttribute("characterList", characterL2Service.findAll());
        model.addAttribute("productL2List", productL2Service.findAll());
        model.addAttribute("adenaReportList", adenaReportService.findAllFetchAll());
        return "report";
    }

    @PostMapping(value = "/main/createAdenaReport", params = "addProduct")
    public String addProduct(@ModelAttribute("adenaReportForm") AdenaReportForm adenaReportForm, Model model){
        model.addAttribute("adenaReportForm", adenaReportService.addProduct(adenaReportForm));
        model.addAttribute("characterList", characterL2Service.findAll());
        model.addAttribute("productL2List", productL2Service.findAll());
        model.addAttribute("adenaReportList", adenaReportService.findAllFetchAll());
        return "report";
    }

    @PostMapping(value = "/main/createAdenaReport", params = "saveReport")
    public String saveReport(@ModelAttribute("adenaReportForm") AdenaReportForm adenaReportForm){
        adenaReportService.saveReport(adenaReportForm);
        return "redirect:/main";
    }

    @GetMapping(value = "/main/report/delete/{id}")
    public String deleteReport(@PathVariable("id") Long id){
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
}
