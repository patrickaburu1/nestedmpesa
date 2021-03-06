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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public Object home(HttpServletRequest request){


      /*  model.addAttribute("appName", "MPESA DASHBOARD");
        model.addAttribute("message", "MESSAGE");
        model.addAttribute("data", "MESSAGE");

        model.addAttribute("transactions", billService.finAllTopups());
        return "transactions";*/

        ModelAndView mv=new ModelAndView();
        mv.addObject("transactions",billService.finAllTopups());
        mv.setViewName("transactions");

        return mv;
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

        ra.addFlashAttribute("status", response);

        return "redirect:/topup";



    }


}
