package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");


        String timezoneParam = req.getParameter("timezone");


        if (timezoneParam == null || timezoneParam.isEmpty()) {
            timezoneParam = "UTC";
        }


        ZoneId zone;

        try {
            if (timezoneParam.equalsIgnoreCase("UTC")) {
                zone = ZoneOffset.UTC;
            } else if (timezoneParam.startsWith("UTC")) {

                String offset = timezoneParam.replace("UTC", "");
                zone = ZoneOffset.of(offset);
            } else {

                zone = ZoneId.of(timezoneParam);
            }
        } catch (Exception e) {

            zone = ZoneOffset.UTC;
            timezoneParam = "UTC";
        }

        LocalDateTime now = LocalDateTime.now(zone);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTime = now.format(formatter) + " " + timezoneParam;

        resp.getWriter().write(
                "<html>" +
                        "<head><title>Time</title></head>" +
                        "<body>" +
                        "<h1>" + formattedTime + "</h1>" +
                        "</body>" +
                        "</html>"
        );
    }
}