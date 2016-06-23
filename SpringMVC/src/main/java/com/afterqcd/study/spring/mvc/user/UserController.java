package com.afterqcd.study.spring.mvc.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by afterqcd on 16/6/22.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @ModelAttribute
    public void populateRequest(HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView userProfile(@PathVariable String name) {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("name", name);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", new User[] {
                new User("bob", 25), new User("jimmy", 18),
                new User("john", 17), new User("shine", 23)
        });
        return modelAndView;
    }
}
