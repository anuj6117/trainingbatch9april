package com.example.demo;

import org.apache.log4j.Logger;

public class LimsLogger {

	final static Logger logger = Logger.getLogger(LimsLogger.class.getName());

	public static void callMeInAppInfo(String parameter) {
		if (logger.isInfoEnabled()) {
			logger.info("This is info : " + parameter);
		}
	}

	public static void callMeInAppDebug(String parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("This is Debug : " + parameter);
		}
	}
	/*
	public void callMeInAppWarn(String parameter) {
		if (logger.isWarnEnabled()) {
			logger.warn("This is warn : " + parameter);
		}
	}
	public void callMeInAppError(String parameter) {
		if (logger.isErrorEnabled()) {
			logger.error("This is error : " + parameter);
		}
	}

	public void callMeInAppFatal(String parameter) {
		if (logger.isFatalEnabled()) {
			logger.fatal("This is fatal : " + parameter);
		}
	}
	*/

	
}
