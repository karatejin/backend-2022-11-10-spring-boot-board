package com.jingu.board.dto.auth;

import com.jingu.board.entity.MemberEntity;

import lombok.AllArgsConstructor;
//import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class GetUserResponseDto { //생성자 get set constructor 만드는 대신 annotation
	private String email;
	private String nickName;
	private String profile;
	private String telNumber;
	private String address;
	
	public GetUserResponseDto(MemberEntity member) { // 생성자 필요한 형태로 가져다 쓴다.
		this.email = member.getEmail();
		this.nickName = member.getNickname();
		this.profile = member.getProfile();
		this.telNumber = member.getTelNumber();
		this.address = member.getAddress();
		
	}
}
