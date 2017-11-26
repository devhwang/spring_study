package co.kr.ucs.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceAdvice {
	//모든 어드바이스는 org.aspectj.lang.JoinPoint 타입의 파라미터를 어드바이스 메서드에 첫번째 매개변수로 선언할 수 있다.
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable{//Around 어드바이스는 JoinPoint의 하위 클래스인 ProceedingJointPoint 타입의 파라미터를 필수적으로 선언해야한다. 
		//getThis() : 프록시 객체를 반환한다
		//getTarget() : 대상 객체를 반환한다
		
		//타겟 메서드의 signature 정보
		String signature = joinPoint.getSignature().toShortString();//getSignature(): 어드바이즈되는 메서드의 설명을 반환한다//toShortString() : 어드바이즈되는 메서드의 설명을 출력한다.
		System.out.println("======================" + signature + "시작");
		//타겟의 메서드가 호출되기 전의 시간
		long start = System.currentTimeMillis();
		
		try {
			//타겟의 메서드 호출
			Object result = joinPoint.proceed();
			return result;
		}finally {
			//타겟의 메서드가 호출된 후의 시간
			long finish = System.currentTimeMillis();
			System.out.println("======================" + signature + " 종료");
			System.out.println("======================" + signature + " 실행시간 ("+(finish - start)+ " ms)");
		}
	}
	
}

//JointPoint : Before 혹은 After 어드바이스에서 사용되는 인터페이스
//ProceedingJoinPoint : Around 어드바이스에서 사용되는 인터페이스(타겟 객체의 앞 뒤에서 실행되는 어드바이스, 앞에서 가로채서 뒤에서도 계속 진행되게 해야하기때문에 proceed메서드를 호출해서 계속 진행되게해야한다)