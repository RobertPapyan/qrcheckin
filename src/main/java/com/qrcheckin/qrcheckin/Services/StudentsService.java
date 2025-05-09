package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Exception.DashboardException;
import com.qrcheckin.qrcheckin.Models.Student;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Repositories.StudentRepository;
import com.qrcheckin.qrcheckin.Requests.StudentsDataRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;


@Service
public class StudentsService {

    private final StudentRepository studentRepository;
    private final AppConfig config;

    public StudentsService(StudentRepository studentRepository, AppConfig config){
        this.studentRepository = studentRepository;
        this.config = config;
    }

    public Page<Student> paginate(User user, StudentsDataRequest request, HttpSession session){

        PageRequest pageRequest;

        var perPage = (int) session.getAttribute("per-page");

        if(Arrays.asList(Student.getSorts()).contains(request.getSort())){
            var sorting = Sort.by(request.getSort());
            if(request.getSort().equals("asc")){
              sorting = sorting.ascending();
            }else{
                sorting = sorting.descending();
            }
            pageRequest = PageRequest.of(request.getPage(),perPage,sorting);
        }else{
            pageRequest = PageRequest.of(request.getPage(),perPage);
        }

        return studentRepository.findStudentsByUserAndOptionalGroupNames(user.getId(),request.getGroup(),request.getSearch(),pageRequest);
    }

    public void delete(Long studentId){
        var student = this.studentRepository.findById(studentId).orElseThrow(() -> new DashboardException("Student not found","Sstudent not found"));

        if(student.getQr() != null){
            try {
                var file = new File(config.getStoragePath() + "/qr_codes/" + student.getQr() );
                Files.delete(file.toPath());
            } catch (IOException e) {
                if( !(e instanceof NoSuchFileException)){
                    var dashboardException = new DashboardException(student.getId() + " failed to delete","Failed to delete student.");
                    dashboardException.initCause(e);
                    throw dashboardException;
                }
            }
        }


        this.studentRepository.delete(student);

    }
}
