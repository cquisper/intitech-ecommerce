package com.cquisper.msvc.payment.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient("rzp_test_Fc7Gf4wjX2OS2x","hkGIPkTczSk0LaOaxnlZ1Qad");
    }
}