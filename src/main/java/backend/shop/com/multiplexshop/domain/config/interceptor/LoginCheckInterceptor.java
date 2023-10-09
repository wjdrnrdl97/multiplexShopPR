package backend.shop.com.multiplexshop.domain.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String redirectURL = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 : {}",redirectURL);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 유저");
            response.sendRedirect("/login?redirectURL="+redirectURL);
            return false;
        }
        return true;
    }
}
