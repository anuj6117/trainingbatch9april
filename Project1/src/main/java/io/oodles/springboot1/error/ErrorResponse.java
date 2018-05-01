package io.oodles.springboot1.error;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class ErrorResponse {
	private String message;
	private List<String> details;
	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}
	
	

}
