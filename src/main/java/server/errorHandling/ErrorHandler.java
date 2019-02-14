package server.errorHandling;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import server.models.ErrorMessageMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ErrorHandler extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException
            (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, ErrorMessageMapper.getMessageFor(ex));
        } catch (IOException e) {
            System.out.println("Error when writing errorResponse");
            return null;
        }
        return modelAndView;
    }
}
