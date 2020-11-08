package com.baixs.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShiroController {
    @GetMapping("/index.html")
    public String toIndexPage() {
        return "index";
    }

    @GetMapping("/login.html")
    public String toLoginPage() {
        return "login";
    }

    @GetMapping("/unauthorization.html")
    public String toUnauthorizationPage() {
        return "unauthorization";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "账户不存在");
            return "login.html";
        } catch (ExcessiveAttemptsException e) {
            model.addAttribute("msg", "认证次数超过限制");
            return "login.html";
        } catch (DisabledAccountException e) {
            model.addAttribute("msg", "账户已禁用");
            return "login.html";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "凭证错误");
            return "login.html";
        } catch (ExpiredCredentialsException e) {
            model.addAttribute("msg", "凭证过期");
            return "login.html";
        }
        return "redirect:home.html";
    }

    @GetMapping("/home.html")
    public String home() {
        return "home";
    }

    @RequiresRoles("admin")
    @GetMapping("/add.html")
    public String add() {
        return "add";
    }

    @RequiresPermissions("user:update")
    @GetMapping("/update.html")
    public String update() {
        return "update";
    }
}
