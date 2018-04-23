package io.oodles.springboot1.enums;

public enum OrderType {
	BUY("buy"),
	SELL("sell");
	
	String value;
	OrderType(String value){}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
  
}
