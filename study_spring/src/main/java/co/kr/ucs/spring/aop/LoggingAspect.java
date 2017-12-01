package co.kr.ucs.spring.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.kr.ucs.spring.controller.BoardController;

@Component
@Aspect
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	//타겟의 메서드가 실행 되기 이전(before) 시점에 처리해야할 필요가 있는 부가기능을 정의한다
    @Before("execution(public * co.kr.ucs.spring..*(..))")
	public void before(JoinPoint joinPoint) {
		String signatureString = joinPoint.getSignature().getName();	
		logger.info("@Before [ " + signatureString + " ] 메서드 실행 전처리 수행");		
		for (Object arg : joinPoint.getArgs()) {
			logger.info("@Before [ " + signatureString + " ] 아규먼트 " + arg);			
		}
		//Joinpoint 앞에서 실행되는 Advice
	}
    
    //타겟의 메서드가 정상적으로 실행된 이후(after) 시점에 처리해야 할 필요가 있는 부가기능을 정의한다.
    @AfterReturning(pointcut="execution(public * co.kr.ucs.spring.service.*.*(..))", returning="ret")
	public void afterReturning(JoinPoint joinPoint, Object ret) {
		String signatureString = joinPoint.getSignature().getName();		
		logger.info("@AfterReturing [ " + signatureString + " ] 메서드 실행 후처리 수행");
		logger.info("@AfterReturing [ " + signatureString + " ] 리턴값=" + ret);
	    //Joinpoint 메서드 호출이 정상적으로 종료된 뒤에 실행되는 Advice
	}
    
    //타겟의 메서드가 예외를 발생한 이후(after) 시점에 처리해야할 필요가 있는 부가기능을 정의한다.
    @AfterThrowing(pointcut="execution(* *..SignService*.*(..))", throwing="ex")
	public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
		String signatureString = joinPoint.getSignature().getName();	
		logger.info("@AfterThrowing [ " + signatureString + " ] 메서드 실행 중 예외 발생");
		logger.info("@AfterThrowing [ " + signatureString + " ] 예외=" + ex.getMessage());
		
	    //예외가 던져질때 실행되는 Advice
	}

	//타겟의 메서드가 실행 된(before) 시점에 처리해야할 필요가 있는 부가기능을 정의한다
    @After("execution(* *..*.*User(..))")
	public void afterFinally(JoinPoint joinPoint) {
		String signatureString = joinPoint.getSignature().getName();
		logger.info("@After [ " + signatureString + " ] 메서드 실행 완료");
		//Joinpoint 뒤에서 실행되는 Advice
	}
}
