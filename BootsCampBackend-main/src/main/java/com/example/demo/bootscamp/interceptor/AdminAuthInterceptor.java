package com.example.demo.bootscamp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // ✅ ยกเว้น OPTIONS request (CORS preflight) ต้องผ่านทุกครั้ง
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // ยกเว้น /admin/login ไม่ต้องเช็ค session
        String uri = request.getRequestURI();
        if (uri.equals("/admin/login")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        // BR-03: ไม่มี session → ไม่ได้ login
        if (session == null || session.getAttribute("adminUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"กรุณาเข้าสู่ระบบก่อน\"}");
            return false;
        }

        // BR-04: มี session แต่ role ไม่ใช่ admin
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"คุณไม่มีสิทธิ์เข้าถึงส่วนนี้\"}");
            return false;
        }

        return true;
    }
}