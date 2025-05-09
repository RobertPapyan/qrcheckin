package com.qrcheckin.qrcheckin.Aspects;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Exception.DashboardException;
import com.qrcheckin.qrcheckin.Exception.api.ApiFailedException;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Aspect
@Component
public class FileSystemAspect {

    private final AppConfig config;
    private final UserRepository userRepository;

    public FileSystemAspect(AppConfig config, UserRepository userRepository){
        this.userRepository = userRepository;
        this.config = config;
    }

    @Before("execution(* com.qrcheckin.qrcheckin.Services.AuthService.storeUser(..))")
    public void ensureImageFolderExists(){
        try {
            var folderPath = Paths.get(config.getPath(), "src","main","resources","static","public","images");
            Files.createDirectories(folderPath);
        }catch (IOException e){
            var dashboardException = new DashboardException(e.getMessage(),"Cant store image");
            dashboardException.initCause(e);
            throw  dashboardException;
        }
    }

    @Before("execution(* com.qrcheckin.qrcheckin.Controllers.api.ApiController.storeStudent(..))")
    public void ensureQrFolderExists(JoinPoint joinPoint){

        try {
            var folderPath = Paths.get(config.getPath(), "src","main","resources","storage","qr_codes" );
            Files.createDirectories(folderPath);
        }catch (IOException e){
            var apiException =   new ApiFailedException("Failed to create directory.");
            apiException.initCause(e);
            throw apiException;
        }


    }
}
