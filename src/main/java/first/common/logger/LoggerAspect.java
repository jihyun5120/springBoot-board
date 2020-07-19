package first.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect { //Controller, Service, DAO가 실행될 때, 어떤 계층의 어떤 메서드가 실행되었는지를 보여주는 역할.
	protected Log log = LogFactory.getLog(LoggerAspect.class);
	static String name = "";
	static String type = "";
	
	//대상 객체의 메서드 실행 전, 후 또는 예외 발생 시점에 공통 기능을 실행
	@Around("execution(* first..controller.*Controller.*(..)) or execution(* first..service.*Impl.*(..)) or execution(* first..dao.*Dao.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if (type.indexOf("Controller") > -1) {
			name = "Controller \t:		";
		} else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl \t:		";	
		} else if (type.indexOf("Dao") > -1) {
			name = "Dao \t\t:		";
		}
		log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		
		return joinPoint.proceed();
	}
}
