package com.example.demo.utilities;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ResponseFormatter {
String message;
Date timeStamp;
HttpStatus status;
Boolean isSuccess;
DataObj data;


    public ResponseFormatter(String message, Date timeStamp, HttpStatus status, Boolean isSuccess, DataObj data) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.status = status;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public DataObj getDataobj() {
        return data;
    }

    public void setDataobj(DataObj dataobj) {
        this.data = dataobj;
    }
}
