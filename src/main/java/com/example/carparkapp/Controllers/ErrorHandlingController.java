package com.example.carparkapp.Controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandlingController implements ErrorController {
    
    @RequestMapping("/error")
    public String error(HttpServletRequest httpServletRequest) {

        Integer code = (Integer) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (code == HttpStatus.NOT_FOUND.value()) {
            return "Error 404 - THE PAGE YOU ARE LOOKING FOR DOES NOT EXIST, OR HAS BEEN REMOVEDS";
        }

        if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "Error 500 - AN INTERNAL SERVER ERROR HAS OCCURRED.";
        }

        return "You have encountered an error.";
    }


}
