package thymeleafLearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import thymeleafLearn.domain.user;
import thymeleafLearn.messages.ConversationMessage;
import thymeleafLearn.messages.MessageType;
import thymeleafLearn.service.OnlineSession;
import thymeleafLearn.service.userService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ashraf on 6/3/15.
 */
@Controller
@RequestMapping(value = "/")
public class Main {
    @Autowired
    userService userService;
    @Autowired
    ProviderSignInUtils signInUtils;
    @Autowired
    RememberMeServices services;
    @Autowired
    PasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute user user, Model model) {
        model.addAttribute("users", userService.findAll());
         return "index";
    }

    @PreAuthorize("hasPermission(1,1)")
    @RequestMapping(params = "id", method = RequestMethod.GET)
    public String user(@RequestParam("id") String id, @ModelAttribute user user, Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(user u, Model model, HttpServletRequest request) {
        userService.save(u);
        if (!u.getFile().isEmpty()) {
            try {///home/ashraf/apache-tomcat-8.0.23/webapps/revox/WEB-INF/classes/static
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(request.getRealPath("") + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "static" + File.separator + u.getImagePath())));
                stream.write(u.getFile().getBytes());
                stream.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
        u.setImagePath("static/files/img.png");
        return "redirect:/";
    }

    @RequestMapping("/signup")
    public String signup(WebRequest request, HttpServletRequest req, HttpServletResponse res) {


        Connection<?> connectionFromSession = signInUtils.getConnectionFromSession(request);
        if (connectionFromSession != null) {
            UserProfile userProfile = connectionFromSession.fetchUserProfile();

            user u = userService.findByEmail(userProfile.getEmail());
            if (u == null) {
                String iid = encoder.encode(UUID.randomUUID().toString());
                u = userService.save(new user(userProfile.getEmail(), iid, userProfile.getName()));
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword(), u.getAuthorities());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(req, res, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            signInUtils.doPostSignUp(userProfile.getEmail(), request);
            services.loginSuccess(req, res, authentication);
            return "redirect:/";
        }
        return "redirect:/error";
    }


    @RequestMapping("/socket")
    public String socket() {
        return "socket";
    }

    @RequestMapping("/videochat")
    public String videochat() {
        return "videochat";
    }

    @RequestMapping(value = "/message")
    @ResponseBody
    public Set<String> message(Principal principal) {
        return onlineSession.WhatMediaPipelineICanAccess(principal.getName());
    }

    @Autowired
    OnlineSession onlineSession;
}
