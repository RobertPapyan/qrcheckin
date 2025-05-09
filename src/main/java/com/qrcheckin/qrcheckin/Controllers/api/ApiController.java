package com.qrcheckin.qrcheckin.Controllers.api;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Exception.api.ApiFailedException;
import com.qrcheckin.qrcheckin.Exception.api.GroupNotFoundException;
import com.qrcheckin.qrcheckin.Helpers.Hasher;
import com.qrcheckin.qrcheckin.Models.Group;
import com.qrcheckin.qrcheckin.Models.Student;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Records.api.ApiResponse;
import com.qrcheckin.qrcheckin.Repositories.GroupRepository;
import com.qrcheckin.qrcheckin.Repositories.StudentRepository;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import com.qrcheckin.qrcheckin.Requests.api.StoreGroupRequest;
import com.qrcheckin.qrcheckin.Requests.api.StoreStudentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.File;
import java.io.IOException;

@RestController
public class ApiController {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AppConfig config;
    private final Hasher hasher;

    public ApiController(
            GroupRepository groupRepository,
            UserRepository userRepository,
            StudentRepository studentRepository,
            AppConfig config,
            Hasher hasher
        ){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.config = config;
        this.hasher = hasher;
    }

    @PostMapping(value = "/api/student", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ApiResponse> storeStudent(@ModelAttribute @Valid StoreStudentRequest request, @RequestHeader(value = "API_KEY") String api_key){

        var proffessor = this.userRepository.findByApiKey(api_key).get();
        var email = request.getEmail();

        var studentResult = this.studentRepository.findByEmail(email);
        var groupResult = this.groupRepository.findByGroupKeyAndProffessor_Id(request.getGroupKey(), proffessor.getId());

        if(!groupResult.isPresent()){
            throw  new GroupNotFoundException("Group with this key does not exist:" + request.getGroupKey());
        }

        var group = groupResult.get();

        if(studentResult.isPresent()){

            var student = studentResult.get();

            if(! student.getGroups().contains(group)){
                student.getGroups().add(group);
                this.studentRepository.save(student);
            }

            var response = new ApiResponse(
                    ApiResponse.ResponseStatuses.SUCCESS,
                    "Student " + student.getEmail() + " exists"
            );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        if(request.getQr() == null || request.getQr().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(ApiResponse.ResponseStatuses.ERROR,"Invalid qr."));
        }

        var qr = request.getQr();
        String image_path;
        try{
            var originalFilename = qr.getOriginalFilename();
            var extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            var hashedName = hasher.hash(originalFilename + System.currentTimeMillis());
            image_path = hashedName + extension;

            qr.transferTo(new File(config.getPath() + "/src/main/resources/storage/qr_codes/"  + image_path));

        }catch (IOException e){
            var apiException = new ApiFailedException("Could not store " + request.getEmail() + " students qr");
            apiException.initCause(e);
            throw apiException;
        }

        var newStudent = new Student(request.getName(),request.getEmail(),image_path);
        newStudent.getGroups().add(group);

        this.studentRepository.save(newStudent);

        var response = new ApiResponse(ApiResponse.ResponseStatuses.SUCCESS,"Student stored successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/api/group")
    public ResponseEntity<ApiResponse> storeGroup(@ModelAttribute @Valid StoreGroupRequest request, @RequestHeader(value = "API_KEY") String api_key){

        var proffessor = this.userRepository.findByApiKey(api_key).get();

        var groups = proffessor.getGroups();

        var matchingGroup = groups.stream().filter((group) -> group.getGroupKey() != null && group.getGroupKey().equals(request.getKey())).findFirst();

        if(matchingGroup.isPresent()){
            var response =  new ApiResponse(ApiResponse.ResponseStatuses.SUCCESS,"Group already created");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        var group = new Group(request.getName(),request.getKey(),proffessor);

        this.groupRepository.save(group);

        var response =  new ApiResponse(ApiResponse.ResponseStatuses.SUCCESS,"Group stored successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
