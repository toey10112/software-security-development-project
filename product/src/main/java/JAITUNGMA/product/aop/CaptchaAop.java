package JAITUNGMA.product.aop;

import JAITUNGMA.product.service.ValidateCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class CaptchaAop {

    @Autowired
    ValidateCaptchaService service;

    //ensures that the below code is executed before the method(s)
    //annotated with the @RequiresCaptcha annotation
    //note - either keep the annotation class in the same package as the aspect class
    //or use the fully qualified name for the annotation class.
    @Around("@annotation(JAITUNGMA.product.annotation.RequiresCaptcha)")
    public Object validateCaptchaResponse(final ProceedingJoinPoint point)
            throws Throwable {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String[]> map = request.getParameterMap();
        String captchaResponse = map.get("g-recaptcha-response")[0];
        //sends it to the service method for validation
        final boolean isValidCaptcha = service.validateCaptcha(captchaResponse);
        if (!isValidCaptcha) {
            log.info("Throwing forbidden exception as the captcha is invalid.");
            return null;
        }
        //if everything is ok the response is returned
        return point.proceed();
    }
}