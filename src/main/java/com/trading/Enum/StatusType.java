package com.trading.Enum;

public enum StatusType {
	
		INACTIVE("inactive"),Active("active");
		
	private String value;

		public String getValue() {
			return value;
		}

		private StatusType(String value) {
			this.value = value;
		}
		
		
		
			}



