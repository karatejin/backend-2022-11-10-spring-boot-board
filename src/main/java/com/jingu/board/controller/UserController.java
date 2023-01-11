package com.jingu.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jingu.board.dto.auth.GetUserResponseDto;
import com.jingu.board.dto.response.ResponseDto;
import com.jingu.board.dto.user.PatchUserDto;
import com.jingu.board.dto.user.PostUserDto;
import com.jingu.board.dto.user.ResultResponseDto;
import com.jingu.board.service.UserService;

@RestController
@RequestMapping("api/user/") // post 로 요청을 보낸다.
public class UserController {  // 회원가입 프로세스
	
	@Autowired UserService userService; // ; 찍으면 저장해라 가만두지 않겠다.
	
	@GetMapping("{email}") //Create
	public ResponseDto<GetUserResponseDto> getUser(@PathVariable ("email")String email){
		return userService.getUser(email);
	}
	
	@PostMapping("") //Read
	public ResponseDto<ResultResponseDto> postUser(@RequestBody PostUserDto requestBody){
		return userService.postUser(requestBody);
	}
	
	@PatchMapping("") // 패치가 성공하면은 유저정보를 다시 겟 한다. //update
	public ResponseDto<GetUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody){
		return userService.patchUser(requestBody);
	}
	
	@DeleteMapping("{email}") // <?> 무엇이든 할 수 있다. 처음 할때만 쓰고 나중에 수정해라. 유지보수 힘들다.
	public ResponseDto<ResultResponseDto> deleteUser(@PathVariable ("email")String email){
		 return userService.deleteUser(email);
	}

}
