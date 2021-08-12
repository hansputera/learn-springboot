package tech.thehanifs.testspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@EnableJdbcHttpSession
public class errorController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger("Error Controller");
    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public String handleError(Exception err) {
        err.printStackTrace();
        logger.error(err.getMessage());
        return err.getMessage();
    }
}
