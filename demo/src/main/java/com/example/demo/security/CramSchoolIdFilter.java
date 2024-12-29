package com.example.demo.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class CramSchoolIdFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        log.info("cramSchoolIdFilterは呼び出されています");
        // リクエストからcramSchoolIdを取得してセッションに保存


            String cramSchoolId = httpRequest.getParameter("cramSchoolId");
            log.info("cramSchoolId:{}",cramSchoolId);
            if (cramSchoolId != null) {
                session.setAttribute("cramSchoolId", cramSchoolId);
                log.info("sessionにCramSchoolIdFilterが情報を保存しました。");
            }
        // フィルター連鎖を続ける
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
