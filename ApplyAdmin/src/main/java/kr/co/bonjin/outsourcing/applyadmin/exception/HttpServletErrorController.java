package kr.co.bonjin.outsourcing.applyadmin.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/*
 * Error Custom Page Controller
 */
@Controller
public class HttpServletErrorController implements ErrorController {

	private final String ERROR_PATH = "/error";

	public HttpServletErrorController() { }

	@RequestMapping("/error")
	public String HandleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return ERROR_PATH+"/404";

			} else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ERROR_PATH+"/500";
				
			} else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return ERROR_PATH+"/403";
			}
		}

		return ERROR_PATH+"/error";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
