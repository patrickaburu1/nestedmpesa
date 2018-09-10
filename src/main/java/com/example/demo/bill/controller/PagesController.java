package com.example.demo.bill.controller;

import com.example.demo.bill.model.Topup;
import com.example.demo.bill.servie.BillService;
import com.example.demo.bill.util.StkPush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;


@Controller
public class PagesController {

    private final BillService billService;

    //String total = tennis.getTotal() != null ? tennis.getTotal() : null;
    private Logger logger=Logger.getLogger(PagesController.class.getSimpleName());


    @Autowired
    public PagesController(BillService billService) {
        this.billService = billService;

    }


    @GetMapping(value = "/home")
    public String home(Model model){
        model.addAttribute("appName", "MPESA DASHBOARD");
        model.addAttribute("message", "MESSAGE");
        model.addAttribute("data", "MESSAGE");

        model.addAttribute("tasks", billService.finAllTopups());

        return "transactions";
    }

    @GetMapping(value = "/topup")
    public String topup(Model model){

        model.addAttribute("topup", new Topup());
        // model.addAttribute("message","null");

        return "topup";
    }

    @PostMapping("/topup")
    public String topUp(@ModelAttribute Topup topup, BindingResult bindingResult, RedirectAttributes ra) {


        StkPush stkPush = new StkPush();

        /*EXECUTE STK PUSH QUERY*/

        String response= billService.topUp1(topup);


        logger.info("to up status  "+response);

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("status", "info");
            // ra.addFlashAttribute("message", "form error make sure you fill inn the fields");
            return "redirect:/topup";
        } else {


            // ra.addFlashAttribute("message", stkPush.getCustomerMessage());
            //ra.addFlashAttribute("status", stkPush.getStatus());
            ra.addFlashAttribute("status", response);

            return "redirect:/topup";
        }

    }


}
