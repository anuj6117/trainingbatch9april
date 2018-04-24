
package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.UserOtp;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

	public UserOtp findByTokenOTP(int tokenOTP);

}
