package com.example.demo.controller;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.User;
import com.example.demo.repository.CramSchoolRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final CramSchoolRepository cramSchoolRepository;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;


    @GetMapping("/login")
    public String login(Model model) {
        List<CramSchool> cramSchools = cramSchoolRepository.findAll();
        model.addAttribute("cramSchools", cramSchools);
        return "auth/login";
    }

    @GetMapping("/success")
    public String loginSuccess(Authentication authentication, @AuthenticationPrincipal UserDetailsImpl userDetails,
                               HttpServletRequest request,
                               Model model) {
        // セッションからcramSchoolIdを取得
        log.info("loginSuccess()が呼び出されています");
        String cramSchoolIdStr = (String) request.getSession().getAttribute("cramSchoolId");
        if (cramSchoolIdStr == null) {
            log.warn("cramSchoolId が見つかりませんでした");
        }else{
            log.info("cramSchoolIdStrが見つかりました。:{}",cramSchoolIdStr);
        }
        int cramSchoolId = Integer.parseInt(cramSchoolIdStr);

        // セッションの破棄
        request.getSession().invalidate();

        // ユーザー情報の更新とセッションの更新処理
        String username = userDetails.getUsername();
        userRepository.findByName(username).ifPresentOrElse(user -> {
            log.info("ユーザーは見つかりました");
            CramSchool cramSchool = cramSchoolRepository.getReferenceById(cramSchoolId);
            user.setCramSchool(cramSchool);
            userRepository.save(user);

            // 認証情報の更新
            updateSecurityContextWithNewAuthentication(username, authentication, request);

            // 更新したユーザー情報をモデルに追加
            model.addAttribute("user", user);

        }, () -> log.warn("ユーザーが見つかりません: {}", username));

        return "index";
    }

    private void updateSecurityContextWithNewAuthentication(String username, Authentication authentication,
                                                            HttpServletRequest request) {
        log.info("updateSecurityContextWithNewAuthenticationが呼び出されました: {}", username);
        UserDetailsImpl updatedUserDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails, authentication.getCredentials(), updatedUserDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(newAuth);

        //sessionを作成して、securityContextを保存する。
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        log.info("session 情報が更新されました");

    }

}
