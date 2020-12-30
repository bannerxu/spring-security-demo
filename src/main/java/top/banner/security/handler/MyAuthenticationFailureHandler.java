package top.banner.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);

    private final String url;

    public MyAuthenticationFailureHandler(String url) {
        this.url = url;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.info("onAuthenticationFailure:{}", exception.getMessage());
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        response.sendRedirect(url);
    }
}
