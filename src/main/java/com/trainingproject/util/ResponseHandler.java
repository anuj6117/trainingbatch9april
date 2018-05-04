
package com.trainingproject.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
public class ResponseHandler
{
	public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message, Object response) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try 
		{
			map.put("timestamp", new Date().getTimezoneOffset());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);
			map.put("data", response);
			return new ResponseEntity<Object>(map, status);
		} 
		catch (Exception e) 
		{
			map.clear();
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	public static ResponseEntity<Object> invalidResponse(HttpStatus status, boolean error, String message)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try 
		{
			map.put("timestamp", new Date().getTime());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);
			return new ResponseEntity<Object>(map, status);
		} 
		catch (Exception e) 
		{
			map.clear();
			map.put("timestamp", new Date().getTime());
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) 
	{
		String errorMsg = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(exception.getMessage());

		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, errorMsg, null);
	}
}