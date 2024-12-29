package com.example.demo.controller;

import com.example.demo.dto.UserRegisterDto;
import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.CramSchoolRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final CramSchoolRepository cramSchoolRepository;

    //todo:教師に複数の校舎を紐づけられるようにする
    //todo:教師の登録内容を編集出来るようにする。
    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("userRegisterDto",new UserRegisterDto());
        List<CramSchool> cramSchools = cramSchoolRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("cramSchools",cramSchools);
        model.addAttribute("roles",roles);
        return "user/register";
    }


    @Transactional
    @PostMapping("/register")
    public String registerUser_p(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam List<Integer> selectedCramSchoolIds,
                                 @ModelAttribute UserRegisterDto userRegisterDto,
                                 RedirectAttributes redirectAttributes){
        User user = userService.createUserFromUserRegisterDto(userRegisterDto,userDetails.getCramSchool());

        String message;
        try{
            userRepository.save(user);
            userService.tieUserToCramSchools(user,selectedCramSchoolIds);

            message="user registry success";
        } catch (RuntimeException e) {
            message="user registry failure";
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/user/show";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") String userId, RedirectAttributes redirectAttributes){
        String message;
        try{
            userRepository.deleteById(Integer.valueOf(userId));
            message = "deletion success";
            redirectAttributes.addFlashAttribute("message",message);
        } catch (RuntimeException e) {
            message="deletion failure";
            redirectAttributes.addFlashAttribute("message",message);
        }

        return "redirect:/user/show";
    }



    @GetMapping("/show")
    public String showUsers(Model model){
        List<CramSchool> cramSchools = cramSchoolRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        userService.setCramSchoolIds(users);

        model.addAttribute("cramSchools",cramSchools);
        model.addAttribute("users",users);
        model.addAttribute("roles",roles);
        return "user/show";
    }


    @PostMapping("/edit")
    public String editUser(@RequestParam("userId")Integer userId,
                           @RequestParam("name")String name,
                           @RequestParam(name="password",required = false)String rowPass,
                           @RequestParam("roleId")Integer roleId,
                           @RequestParam(name="cramSchoolIds",required = false)List<Integer> cramSchoolIds,
                           Model model){

        userService.updateUser(userId,name,rowPass,roleId,cramSchoolIds);

        return "redirect:/user/show";
    }
}
