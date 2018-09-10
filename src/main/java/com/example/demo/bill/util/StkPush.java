package com.example.demo.bill.util;

import java.io.Serializable;

public class StkPush  implements Serializable {

     String CustomerMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

     String status;

    public String getCustomerMessage() {
        return CustomerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        CustomerMessage = customerMessage;
    }



}
