package tech.thehanifs.testspring;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@ControllerAdvice
@EnableJdbcHttpSession
public class errorController implements ErrorController {

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, HttpServletResponse response, Exception err) throws IOException {
        HttpSession session = req.getSession();
        response.addHeader("session_id", session.getId());
        return err.getMessage();
    }
}
