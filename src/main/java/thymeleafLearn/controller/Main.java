package thymeleafLearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import thymeleafLearn.domain.user;
import thymeleafLearn.service.userService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        model.addAttribute("users", userService.findall());
        return "index";
    }

    @PreAuthorize("hasPermission(1,1)")
    @RequestMapping(params = "id", method = RequestMethod.GET)
    public String user(@RequestParam("id") String id, @ModelAttribute user user, Model model) {
        model.addAttribute("users", userService.findall());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(user u, Model model, HttpServletRequest request) {
        userService.save(u);
        if (!u.getFile().isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(request.getRealPath("") + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "static/" + File.separator + u.getImagePath())));
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
        UserProfile userProfile = signInUtils.getConnectionFromSession(request).fetchUserProfile();

        user u = userService.findByEmail(userProfile.getEmail());
        if (u == null) {
            String iid = encoder.encode(UUID.randomUUID().toString());
            u = userService.save(new user(userProfile.getEmail(), iid, userProfile.getName()));
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword(), u.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        signInUtils.doPostSignUp(userProfile.getEmail(), request);
        services.loginSuccess(req, res, authentication);
        return "redirect:/";
    }
    @RequestMapping("/socket")
    public String socket() {
        return "socket";
    }

}
