package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Web service response")
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Invocation result (value 'success' means everything's OK but value 'error' means there was an error)")
	private String result;
	
	@Schema(description = "Data returned by the WS, its estructure depends on the WS")
	private Object data;
	
	@Schema(description = "Message (used usually when there was an error)")
	private String message;

}
