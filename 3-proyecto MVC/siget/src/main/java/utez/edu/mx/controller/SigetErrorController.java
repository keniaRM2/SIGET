package utez.edu.mx.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.util.Utileria;

@Controller
public class SigetErrorController extends BaseController implements ErrorController {

    @RequestMapping(PathConstants.ERROR)
    public String mostrarError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Utileria.nonNull(status)) {
            int statusCode = Integer.parseInt(status.toString());
            System.out.println(statusCode);
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return VistasConstants.ERROR_404;
            }else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return VistasConstants.ERROR_500;
            }
        }

        return VistasConstants.ERROR;
    }
}
