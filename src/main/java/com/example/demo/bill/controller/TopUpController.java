package com.example.demo.bill.controller;

import com.example.demo.bill.model.Topup;
import com.example.demo.bill.repository.TopupRespository;
import com.example.demo.bill.servie.BillService;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/topup")
public class TopUpController {

    private final TopupRespository topupRespository;

    private final BillService billService;

    private Logger logger=Logger.getLogger(TopUpController.class.getSimpleName());

    @Autowired
    public TopUpController(TopupRespository topupRespository, BillService billService) {
        this.topupRespository = topupRespository;
        this.billService = billService;
    }

    @RequestMapping(value = "/wallet", method = RequestMethod.POST)
 /*   @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER') or hasAuthority('CUSTOMER_USER')")*/
    public @ResponseBody Object topup(@Valid Topup topup){

        try {

            logger.info("\n"+"data received "+topup+"\n");

            billService.getMpesaToken();
            return  ResponseEntity.status(HttpStatus.OK).body(billService.topUp1(topup));

        }catch (Exception ex){

            logger.info("error "+ex.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    /* GET TRANSACTION HISTORY  */
    @GetMapping(value = "/history")
  /*  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER') or hasAuthority('CUSTOMER_USER')")*/
    public  @ResponseBody Object history(){

        try {
            Iterable<Topup> response=billService.finAllTopups();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    /* Filter by transaction */
    @GetMapping(value = "/filter")
    public @ResponseBody Object filter(@Param(value = "amount") Double amount ){

        logger.info("\n"+"filter "+amount+"\n");

        try {
          return   topupRespository.findByAmount(amount);

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
