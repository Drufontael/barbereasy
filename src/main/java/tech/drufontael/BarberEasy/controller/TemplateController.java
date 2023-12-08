package tech.drufontael.BarberEasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping(value = "customer/register")
    public String registerCustomer(Model model){
        return "register-customer";
    }
    @GetMapping(value = "customer/list")
    public String listCustomer(Model model){
        return "list-customer";
    }
}
