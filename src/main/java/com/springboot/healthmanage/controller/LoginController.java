package com.springboot.healthmanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // "login.html" を表示
    }

//    // (参考) ログイン成功後のダッシュボードページ
//    @GetMapping("/dashboard")
//    public String showDashboard() {
//        return "dashboard"; // "dashboard.html" を表示
//    }
}