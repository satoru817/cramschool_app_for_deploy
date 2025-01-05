package com.example.demo.controller.shared;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.School;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final SchoolRepository schoolRepository;


    //OK
    @GetMapping("/registerSchool")
    public String registerSchool_g(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        CramSchool cramSchool = userDetails.getCramSchool();


        if(!model.containsAttribute("school")){
            School school = new School();
            model.addAttribute("school",school);
        }

        //ログインしている教室と紐づく学校をすべて選択する
        List<School> schoolList = schoolRepository.getAllSchoolByCramSchool(cramSchool);
        model.addAttribute("schoolList",schoolList);


        return "school/school_register";
    }


    //OK
    @PostMapping("/registerSchool")
    public String registerSchool_p(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   Model model,
                                   @ModelAttribute @Validated School school,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()){
            school.setCramSchool(userDetails.getCramSchool());
            schoolService.save(school);
        }else{
            redirectAttributes.addFlashAttribute("school",school);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            +"school",bindingResult);
        }
        return "redirect:/registerSchool";

    }

    @GetMapping("/school/delete/{id}")
    public String delete(@PathVariable(name="id")Integer id, RedirectAttributes redirectAttributes){

        schoolService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage","学校を削除しました。");

        return "redirect:/registerSchool";
    }

}
