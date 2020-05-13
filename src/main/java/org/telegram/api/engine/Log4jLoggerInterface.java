package org.telegram.api.engine;

import org.apache.log4j.Logger;

public class Log4jLoggerInterface implements LoggerInterface {
	private static Logger log = Logger.getLogger("org,telegram");
	
	@Override
	public void w(String tag, String message) {
		log.warn(tag + "-" + message);
	}
	
	@Override
	public void d(String tag, String message) {
		log.debug(tag + "-" + message);
		
	}
	
	@Override
	public void e(String tag, Throwable t) {
		log.error(tag, t);
		
	}
}
