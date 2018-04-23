package com.trading.Enum;

public enum OrderStatus {


	
		PENDING("pending"),COMPLETE("complete"), MODERATE("moderate");
		
	private String value;

		public String getValue() {
			return value;
		}

		private OrderStatus(String value) {
			this.value = value;
		}
		
		
		
			}



