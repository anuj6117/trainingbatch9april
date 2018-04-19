package io.oodles.springboot1.enums;

public enum Status {
INACTIVE("inactive"),ACTIVE("active");
public String value;



public String getValue() {
	return value;
}

/*public void setValue(String value) {
	this.value = value;
}*/

private Status(String value) {
	this.value = value;
}

}
