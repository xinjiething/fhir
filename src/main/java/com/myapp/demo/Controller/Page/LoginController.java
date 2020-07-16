package com.myapp.demo.Controller.Page;

import com.myapp.demo.Model.DisplayOptionModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("Page")
@Controller
public class LoginController {

    /**
     * This function is used to render the login page view
     * 
     * @return String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "redirect:/login";
    }

    /**
     * 
     * This function is used to render the login page view
     * 
     * @return String
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {
        return "LoginPage";
    }

    /**
     * 
     * This function is used to received the identifier that is key in by the health
     * practitioner
     * 
     * @param id    : health practitioner
     * @param model
     * @return String
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPagePost(@RequestParam("identifier") String id, Model model) {
        model.addAttribute("identifier", id);
        model.addAttribute("Page", new DisplayOptionModel());
        return "forward:/Homepage";
    }

    /**
     * This function is used to render the error page
     * 
     * @return String
     */
    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginErrorGet() {
        return "LoginErrorPage";
    }

    /**
     * This function is used to redirect Get method of the loginError page
     * 
     * @return String
     */
    @RequestMapping(value = "/loginError", method = RequestMethod.POST)
    public String loginErrorPost() {
        return "redirect:/login";
    }

    /** 
     * Redirect to GET method of '/login' route
     */
    public String errorPagePost() {
        return "redirect:/login";
    }
    
}