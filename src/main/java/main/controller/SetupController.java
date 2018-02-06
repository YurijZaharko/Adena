package main.controller;

import main.entity.CharacterL2;
import main.entity.ProductL2;
import main.entity.constants.ServerNames;
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
public class SetupController {

    private CharacterL2Service characterL2Service;
    private ProductL2Service productL2Service;

    @ModelAttribute("characterL2")
    public CharacterL2 characterL2() {
        return new CharacterL2();
    }

    @ModelAttribute("productL2")
    public ProductL2 productL2() {
        return new ProductL2();
    }

    @GetMapping("/main/createCharacter")
    public String showCharacters(Model model) {
        model.addAttribute("characterL2List", characterL2Service.findAll());
        model.addAttribute("serverList", ServerNames.values());
        return "character";
    }

    @PostMapping("/main/createCharacter")
    public String saveCharacter(@ModelAttribute("characterL2") CharacterL2 characterL2) {
        characterL2Service.save(characterL2);
        return "redirect:/main/createCharacter";
    }

    @GetMapping("/main/createCharacter/edit/{id}")
    public String editCharacter(@PathVariable("id") Long id, Model model) {
        model.addAttribute("characterL2", characterL2Service.findOne(id));
        model.addAttribute("serverList", ServerNames.values());
        return "character";
    }

    @GetMapping("/main/createCharacter/delete/{id}")
    public String deleteCharacter(@PathVariable("id") Long id) {
        characterL2Service.delete(id);
        return "redirect:/main/createCharacter";
    }

    @GetMapping("/main/createProduct")
    public String showProductL2(Model model) {
        model.addAttribute("productL2List", productL2Service.findAll());
        return "product";
    }

    @PostMapping("/main/createProduct")
    public String saveProductL2(@ModelAttribute ProductL2 productL2) {
        productL2Service.save(productL2);
        return "redirect:/main/createProduct";
    }

    @GetMapping("/main/createProduct/edit/{id}")
    public String editProductL2(Model model, @PathVariable("id") Long id) {
        model.addAttribute("productL2", productL2Service.findOne(id));
        return "product";
    }

    @GetMapping("/main/createProduct/delete/{id}")
    public String deleteProductL2(@PathVariable("id") Long id) {
        productL2Service.delete(id);
        return "redirect:/main/createProduct";
    }

    @Autowired
    public void setCharacterL2Service(CharacterL2Service characterL2Service) {
        this.characterL2Service = characterL2Service;
    }

    @Autowired
    public void setProductL2Service(ProductL2Service productL2Service) {
        this.productL2Service = productL2Service;
    }
}
