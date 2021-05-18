package com.oms.user.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.oms.user.exception.UserMsException;

@Component
@Aspect
public class LoggingAspect {
	
	@AfterThrowing(pointcut = "execution(* com.oms.user.service.*Impl.*(..))", throwing = "exception")	
	public void logExceptionFromService(Exception exception) throws UserMsException {
			log(exception);
	}

	
	private void log(Exception exception) {
		Logger logger = LogManager.getLogger(this.getClass());
		if(exception.getMessage()!=null ){
			
			logger.info(exception.getMessage());
		}
		else{
			logger.info(exception.getMessage(), exception);
		}
	}

}

//&& 
//(exception.getMessage().contains("Service") || exception.getMessage().contains("Validator"))
