package com.example.demo.controller;


import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Student;
import com.example.demo.repository.CramSchoolRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cramSchool")
public class CramSchoolController {
    private final CramSchoolRepository cramSchoolRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/register")
    public String registerCramSchool(Model model){

        return "cramSchool/register";
    }

    @PostMapping("/register")
    public String registerCramSchool_p(@RequestParam("cramSchoolName")String cramSchoolName,
                                 RedirectAttributes redirectAttributes){
        CramSchool cramSchool = new CramSchool();
        cramSchool.setName(cramSchoolName);


        String message;
        try{
            cramSchoolRepository.save(cramSchool);
            createAverageStudentForCramSchool(cramSchool);
            message="cramSchool registry success";
        } catch (RuntimeException e) {
            message="cramSchool registry failure";
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/cramSchool/show";
    }

    @PostMapping("/delete")
    public String deletecramSchool(@RequestParam("cramSchoolId") String cramSchoolId, RedirectAttributes redirectAttributes){
        String message;
        try{
            cramSchoolRepository.deleteById(Integer.valueOf(cramSchoolId));
            message = "deletion success";
            redirectAttributes.addFlashAttribute("message",message);
        } catch (RuntimeException e) {
            message="deletion failure";
            redirectAttributes.addFlashAttribute("message",message);
        }

        return "redirect:/cramSchool/show";
    }



    @GetMapping("/show")
    public String showCramSchools(Model model){
        List<CramSchool> cramSchools = cramSchoolRepository.findAll();
        model.addAttribute("cramSchools",cramSchools);
        return "cramSchool/show";
    }

    public void createAverageStudentForCramSchool(CramSchool cramSchool){
        Student student = new Student();
        student.setCramSchool(cramSchool);
        student.setName("ave");
        student.setCode((long) cramSchool.getCramSchoolId());
        student.setEl1(2000);
        studentRepository.save(student);
    }
}

