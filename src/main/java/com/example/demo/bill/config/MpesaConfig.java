package com.example.demo.bill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpesaConfig {
    @Value("${mpesa.app.key}")
    private String app_key;

    @Value("${mpesa.app.secret}")
    private String app_secret_key;

    @Value("${mpesa.shortcode}")
    private String shortcode;

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getStkUrl() {
        return stkUrl;
    }

    public void setStkUrl(String stkUrl) {
        this.stkUrl = stkUrl;
    }

    @Value("${mpesa.auth.url}")
    private String authUrl;

    @Value("${mpesa.stkpush.url}")
    private String stkUrl;


    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret_key() {
        return app_secret_key;
    }

    public void setApp_secret_key(String app_secret_key) {
        this.app_secret_key = app_secret_key;
    }

    public String getPass_key() {
        return pass_key;
    }

    public void setPass_key(String pass_key) {
        this.pass_key = pass_key;
    }

    @Value("${mpesa.pass.key}")
    private String pass_key;


}
