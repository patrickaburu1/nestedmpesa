package com.example.demo.bill.servie;

import com.example.demo.bill.model.Topup;
import com.example.demo.bill.util.ApiToken;

import java.util.List;
public interface BillService {


    ApiToken getMpesaToken();

    List<Topup> finAllTopups();

    Topup topUp(Topup topup);

   String topUp1(Topup topup);



}
