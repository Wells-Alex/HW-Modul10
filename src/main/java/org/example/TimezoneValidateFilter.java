package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String timezone = req.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            try {

                if (timezone.startsWith("UTC")) {

                    String offset = timezone.replace("UTC", "");
                    ZoneId.of("UTC" + offset);
                } else {
                    ZoneId.of(timezone);
                }
            } catch (Exception e) {

                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write("Invalid timezone");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}