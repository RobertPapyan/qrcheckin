package com.qrcheckin.qrcheckin.Exception;

import com.qrcheckin.qrcheckin.Records.FlashMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DashboardException.class)
    public String dashboardHandler(DashboardException e, RedirectAttributes redirectAttributes){

        log.error(e.getMessage());

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(FlashMessage.FlashStatuses.ERROR,e.getDisplayMessage())
        );

        if(e instanceof HomeException){
            return "redirect:/";
        }

        return "redirect:/dashboard/profile";
    }


}
