package com.insidetrackdata.test.util;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RequestUtil {

    private static final String X_FORWARDED_FOR_HEADER = "X-FORWARDED-FOR";

    public static String getIpAddressClient(HttpServletRequest request) {
        String ipAddressClient = null;
        try{
        	String ipsForwarded = request.getHeader(X_FORWARDED_FOR_HEADER);
        	if(ipsForwarded != null){
        		int index = ipsForwarded.indexOf(","); 
        		if(index != -1){
        			ipAddressClient = ipsForwarded.substring(0, index).trim();
        		}else{
        			ipAddressClient = ipsForwarded;
        		}
        	}else{
        		ipAddressClient = request.getRemoteAddr();
        	}
        }catch(Exception ex){
        	log.error("Couldn't get the client IP from the request object", ex);
        }
        return ipAddressClient;
    }

}
