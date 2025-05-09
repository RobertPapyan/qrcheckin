package com.qrcheckin.qrcheckin.Controllers;

import com.qrcheckin.qrcheckin.Exception.HomeException;
import com.qrcheckin.qrcheckin.Records.FlashMessage;
import com.qrcheckin.qrcheckin.Repositories.StudentRepository;
import com.qrcheckin.qrcheckin.Services.MailerService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class GetQrController {

    private final StudentRepository studentRepository;
    private final MailerService mailer;
    public GetQrController(StudentRepository studentRepository, MailerService mailer){
        this.studentRepository = studentRepository;
        this.mailer = mailer;
    }

    @GetMapping("/get-qr")
    public String getQr(){ return "views/student/get-qr"; }

    @GetMapping("/get-qr/student/exists")
    public ResponseEntity<?> studentExists(@RequestParam("email") @NotBlank String email) {
        boolean studentExists = studentRepository.existsByEmail(email);
        return ResponseEntity.ok(new Object() {
            public final boolean exists = studentExists;
        });
    }

    @PostMapping("/get-qr")
    public String sendQrToEmail(@RequestParam Map<String,String> request, RedirectAttributes redirectAttributes){

        var email = request.get("email");

        var student = this.studentRepository.findByEmail(email)
                .orElseThrow(() -> new HomeException(email + " student not found","Email not fond"));

        this.mailer.sendStudentQr(student);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(
                        FlashMessage.FlashStatuses.SUCCESS,
                        "Your QR will be sent to your email!")
        );
        return "redirect:/";
    }

}
