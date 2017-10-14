/** */
package cn.com.jicongg.config.spring.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author cong.ji
 *     <p>Date: 2017-10-13
 */
@Aspect
@Component
public class ControllerAop {

  private final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

  @Pointcut("execution(* com.lamezhi..web.controller..*.*(..))")
  public void execute() {}

  @Before(value = "execute()")
  public void doBefore(JoinPoint joinPoint) {
    String targetName = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    HttpSession session = request.getSession();
    logger.info("class:" + targetName + " method:" + methodName + " session id:" + session.getId());
  }
}
