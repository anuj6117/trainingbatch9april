package com.trading.dto;

import com.trading.Enum.RoleType;

public class UserRoleDto {
		private long userId;
		private RoleType roleType;
		
		public RoleType getRoleType() {
			return roleType;
		}
		
		public void setRoleType(RoleType roleType) {
			this.roleType = roleType;
		}
		public long getuserId() {
			return userId;
		}
		public void setuserId(long userId) {
			this.userId = userId;
		}
		
		
	}
