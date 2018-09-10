package com.example.demo.bill.servie.impl;

import com.example.demo.bill.config.MpesaConfig;
import com.example.demo.bill.model.Topup;
import com.example.demo.bill.repository.TopupRespository;
import com.example.demo.bill.servie.BillService;
import com.example.demo.bill.util.ApiToken;
import com.example.demo.bill.util.StkPush;
import com.google.gson.Gson;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class BillServiceImpl implements BillService {
    private final
    TopupRespository topupRespository;

    private final
    MpesaConfig mpesaConfig;

    /*  Logger logger = Logger.getLogger(BillService.class.getSimpleName());*/
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson;
    private ApiToken apiToken;
    private StkPush stkPush=new StkPush();


    @Autowired
    public BillServiceImpl(Gson gson, TopupRespository topupRespository, MpesaConfig mpesaConfig) {
        this.gson = gson;
        this.topupRespository = topupRespository;
        this.mpesaConfig = mpesaConfig;
    }

    @Override
    public List<Topup> finAllTopups() {

        return (List<Topup>) topupRespository.findAll();
    }

    @Override
    public Topup topUp(Topup topup) {

        logger.info("\n" + "stk push  " + stkPush.getCustomerMessage() + " \n");

        return topupRespository.save(topup);
    }

    @Override
    public String topUp1(Topup topup) {

        //getMpesaToken();

        logger.info("\n" + "arrived " + " \n");
        //  String token = apiToken.getAccess_token();
        String token ="test";

        /*first log that skt push*/
        topupRespository.save(topup);

        /*if (token != null) {*/
        /*do an mpesa stk push*/
        OkHttpClient client = new OkHttpClient();

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String shortcode = mpesaConfig.getShortcode();
        String passkey = mpesaConfig.getPass_key();
        String pass = shortcode + "" + passkey + "" + timeStamp;
        String msisdn = topup.getMsisdn();
        Double amount = topup.getAmount();
        /*Encode password with base 64 */
        String password = Base64.getEncoder().encodeToString(pass.getBytes());

        RequestParams params = new RequestParams();
        params.setShortcode(shortcode);
        params.setPassword(password);
        params.setTimestamp(timeStamp);
        params.setAmount(amount);
        params.setCallBackUrl("https://google.com/");
        params.setTransactionDesc("test");
        params.setTransactionType("CustomerPayBillOnline");
        params.setPartyA(msisdn);
        params.setPartyB(shortcode);
        params.setPhoneNumber(msisdn);
        params.setAccountReference("test");

        HashMap<String, Object> re = new HashMap<>();
        re.put("state", "error");

        logger.info("\n" + "msisdn " + topup.getMsisdn() + " \n");
        logger.info("\n" + "stk push data " + params + " \n");
        // logger.info("\n" + "mpesa token  " + apiToken.getAccess_token() + " \n");

        /*    MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, gson.toJson(params));
            Request request = new Request.Builder()
                    .url(mpesaConfig.getStkUrl())
                    .post(body)
                    .addHeader("authorization", "Bearer " + apiToken.getAccess_token())
                    .addHeader("content-type", "application/json")
                    .build();*/


        Request request = new Request.Builder()
                .url("http://localhost:8080/topup/history")
                .addHeader("content-type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("\n" + "status code  " + response.code() + " \n");
            if (response.code() == 200) {
                logger.info("\n" + "response 200  " + response.body().string() + " \n");

                stkPush.setStatus("success");
                stkPush.setCustomerMessage("Request accepted for processing");

                logger.info("stk pushed successfully customer  ", stkPush.getCustomerMessage());
                return "success";
            } else {

                logger.info("\n" + "response not success  code "+response.code()+" body " + response.body().string() + " \n");

                stkPush.setStatus("error");
                stkPush.setCustomerMessage("failed please try again");

                return "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("\n" + "exception  " + e.getMessage() + " \n");
        }

        return  null;


        /*    }
         *//*failed to generate mpesa token token*//*
        else {
            logger.info("\n" + "Failed to generatetoken " + " \n");
            return null;
        }*/

    }

    /*generate mpesa token*/
    @Override
    public ApiToken getMpesaToken() {
        Response response;
        // Use base64 to encode the consumer key and secret.
        String app_key = mpesaConfig.getApp_key();
        String app_secret = mpesaConfig.getApp_secret_key();
        String appKeySecret = app_key + ":" + app_secret;

        //String auth = Base64.encode(bytes);
        String auth = Base64.getEncoder().encodeToString(appKeySecret.getBytes());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mpesaConfig.getAuthUrl())
                .get()
                .addHeader("authorization", "Basic " + auth)
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            response = client.newCall(request).execute();

            if (response.code() == 200) {
                apiToken = gson.fromJson(response.body().string(), ApiToken.class);
                logger.info("Access token given ", apiToken);
                return apiToken;
            } else if (response.code() == 401) {
                logger.error("Could not get access token. Access denied.Invalid credentials : {}", response.body().string());
            } else {
                logger.error("Error occurred fetching api access token : {} ", response.body().string());
            }
            logger.info("\n" + "token generation  " + response.body().string() + " \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*stk push params/

     */
    private class RequestParams implements Serializable {
        private String BusinessShortCode;
        private String Password;
        private String TransactionType;
        private Double Amount;
        private String PartyA;
        private String PartyB;
        private String PhoneNumber;
        private String CallBackURL;
        private String AccountReference;
        private String TransactionDesc;
        private String Timestamp;

        public void setTimestamp(String timestamp) {
            Timestamp = timestamp;
        }

        public String getTimestamp() {
            return Timestamp;
        }

        RequestParams() {
        }

        public String getShortcode() {
            return BusinessShortCode;
        }

        public void setShortcode(String businessShortCode) {
            this.BusinessShortCode = businessShortCode;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            this.Password = password;
        }

        public String getTransactionType() {
            return TransactionType;
        }

        public void setTransactionType(String transactionType) {
            this.TransactionType = transactionType;
        }

        public Double getAmount() {
            return Amount;
        }

        public void setAmount(Double amount) {
            this.Amount = amount;
        }

        public String getPartyA() {
            return PartyA;
        }

        public void setPartyA(String partyA) {
            this.PartyA = partyA;
        }

        public String getPartyB() {
            return PartyB;
        }

        public void setPartyB(String partyB) {
            this.PartyB = partyB;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.PhoneNumber = phoneNumber;
        }

        public String getCallBackUrl() {
            return CallBackURL;
        }

        public void setCallBackUrl(String callBackUrl) {
            this.CallBackURL = callBackUrl;
        }

        public String getAccountReference() {
            return AccountReference;
        }

        public void setAccountReference(String accountReference) {
            this.AccountReference = accountReference;
        }

        public String getTransactionDesc() {
            return TransactionDesc;
        }

        public void setTransactionDesc(String transactionDesc) {
            this.TransactionDesc = transactionDesc;
        }
    }
}
