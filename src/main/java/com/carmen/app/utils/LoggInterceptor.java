package com.carmen.app.utils;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggInterceptor {

	@AroundInvoke
	public Object processMethod(InvocationContext context) throws Exception {
		Logger log = LogManager.getLogger(context.getClass());

		try {
			log.info("Called: " + context.getMethod());
			Object result = context.proceed();
			return result;
		} catch (Exception ex) {
			log.error("Error");
			throw ex;

		}

	}
}
