package com.insidetrackdata.test.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

public final class MessagesUtil {

	private static final MessageSource MENSAJES;
	
	static {
		ResourceBundleMessageSource messagesSource = new ResourceBundleMessageSource();
		messagesSource.setBasename("messages");
	    MENSAJES = messagesSource;
	}
	
	public static String getMessage(String key){
		try{
			return MENSAJES.getMessage(key, null, LocaleContextHolder.getLocale());
		}catch(Exception ex){
			return null;
		}
	}

}
