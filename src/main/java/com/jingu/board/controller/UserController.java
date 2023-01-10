package com.jingu.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jingu.board.dto.auth.GetUserResponseDto;
import com.jingu.board.dto.response.ResponseDto;
import com.jingu.board.dto.user.PostUserDto;
import com.jingu.board.dto.user.PostUserResponseDto;
import com.jingu.board.service.UserService;

@RestController
@RequestMapping("api/user/") // post 로 요청을 보낸다.
public class UserController {  // 회원가입 프로세스
	
	@Autowired UserService userService; // ; 찍으면 저장해라 가만두지 않겠다.
	
	@GetMapping("{email}")
	public ResponseDto<GetUserResponseDto> getUser(@PathVariable ("email")String email){
		return userService.getUser(email);
	}
	
	@PostMapping("")
	public ResponseDto<PostUserResponseDto> postUser(@RequestBody PostUserDto requestBody){
		return userService.postUser(requestBody);
	}
	

}
