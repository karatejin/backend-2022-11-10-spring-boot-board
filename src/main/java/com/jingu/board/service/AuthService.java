package com.jingu.board.service;

import org.springframework.stereotype.Service;

import com.jingu.board.dto.auth.AuthPostDto;
import com.jingu.board.dto.auth.LoginDto;
import com.jingu.board.dto.response.ResponseDto;

//@Service : 해당클래스가 Service 레이ㅓ 역활을 함
@Service
public class AuthService {
	
	public ResponseDto<LoginDto> login(AuthPostDto dto){
		LoginDto result = new LoginDto("JWT",3600000);
		return ResponseDto.setSuccess("", result);
	}

}
