package com.insidetrackdata.test.util;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class ResponseUtil {

    public static HttpHeaders generateHeadersDownloadFile(String filename, int fileSize) {
        HttpHeaders headers = new HttpHeaders();
        
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(filename);
		headers.setContentType(MediaType.valueOf(mimeType));
		
		ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(filename).build();
		headers.setContentDisposition(contentDisposition);
		
		headers.add("filename", filename);
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "filename");
		headers.setContentLength(fileSize);
		
        return headers;
    }

}
