package com.yaavarea.server.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {
    @GetMapping("/user")
    public Integer getUser(HttpSession session) {
        Integer counter = (Integer) session.getAttribute("count");

        System.out.println(session.getId());

        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }

        session.setAttribute("count", counter);
        return counter;
    }
}
