package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Models.Student;
import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Repositories.StudentRepository;
import com.qrcheckin.qrcheckin.Requests.StudentsDataRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class StudentsService {

    private final StudentRepository studentRepository;

    public StudentsService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
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
}
