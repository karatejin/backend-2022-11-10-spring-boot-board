package com.jingu.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jingu.board.dto.auth.GetUserResponseDto;
import com.jingu.board.dto.response.ResponseDto;
import com.jingu.board.dto.user.PatchUserDto;
import com.jingu.board.dto.user.PostUserDto;
import com.jingu.board.dto.user.ResultResponseDto;
import com.jingu.board.entity.MemberEntity;
import com.jingu.board.repository.MemberRepository;

@Service
public class UserService { // C, R 을 했다.

	@Autowired
	MemberRepository memberRepository;

	public ResponseDto<List<GetUserResponseDto>>getAllUser(){
		List<MemberEntity> memberList = memberRepository.findAll();
		List<GetUserResponseDto> data = new ArrayList<GetUserResponseDto>();
		for(MemberEntity member: memberList) {
		data.add(new GetUserResponseDto(member));
		}
		
		return ResponseDto.setSuccess("get user list Success", data);
	}
	
	public ResponseDto<GetUserResponseDto> getUser(String email) {
	

		// 해당 이메일을 데이터베이스에서 검색
		MemberEntity member;

		try {
			member = memberRepository.findById(email).get();
		}
		// 존재하지 않으면 "Not Exist User" 메세지를 포함한 Failed Response 반환
		catch (Exception e) {

			return ResponseDto.setFailed("Not Exist User");
		}

//		if (member.equals(email))
//		return ResponseDto.setSuccess("", null);

		// 존재하면 User정보 반환
		// 1
//		GetUserResponseDto responseData = new GetUserResponseDto();
//		responseData.setEmail(member.getEmail());
//		responseData.setNickName(member.getNickname());
//		responseData.setProfile(member.getProfile());
//		responseData.setTelNumber(member.getTelNumber());
//		responseData.setAddress(member.getAddress());
//		
//		return ResponseDto.setSuccess("Get User Success", responseData);

		// 2 GetUserResponseDto 에 builder 가 있어야 한다.
//		GetUserResponseDto responseData =
//				GetUserResponseDto
//				.builder()
//				.email(member.getEmail())
//				.nickName(member.getNickname())
//				.profile(member.getProfile())
//				.telNumber(member.getTelNumber())
//				.address(member.getAddress())
//				.build();
//		return ResponseDto.setSuccess("Get User Success", responseData);

		// 3
//		return ResponseDto.setSuccess("Get User Success", 
//				new GetUserResponseDto(
//						member.getEmail(),
//						member.getNickname(),
//						member.getProfile(),
//						member.getTelNumber(),
//						member.getAddress()
//						)
//				);
		// 선생님

		return ResponseDto.setSuccess("Get User Success", new GetUserResponseDto(member));

	}

	public ResponseDto<ResultResponseDto> postUser(PostUserDto dto) { // 모든 메소드는 검증 해야한다.
		// 데이터베이스에 해당 이메일이 존재하는지 체크
		// 존재한다면 Failed Response 를 반환
		// Select * From m Where email =?
		// 결과가 존재하느냐? 존재하지 않으면 중복되지 않는다. 그래서 존재하면 중복되지 않게해야함
		String email = dto.getEmail();

		// if(memberRepository.existsById(email))
		// return ResponseDto.setFailed("이미 존재하는 이메일 입니다."); 이렇게해도 된다만
		// try catch 문 안에 넣어야 한다.

		try {
			if (memberRepository.existsById(email))
				return ResponseDto.setFailed("이미 존재하는 이메일 입니다.");
		} catch (Exception e) {
			return ResponseDto.setFailed("데이터베이스 오류 입니다.");
		}

//		try {
//			MemberEntity member = memberRepository.findById(email).get();
//		}catch(Exception e){
//			return ResponseDto.setFailed("이미 존재하는 이메일 입니다.");
//		}

		String password = dto.getPassword();
		String password2 = dto.getPassword2();

		if (!password.equals(password2))
			return ResponseDto.setFailed("비밀번호가 서로 다릅니다.");

		MemberEntity member = MemberEntity // MemberEntity 를 통해서 //MemberEntity member = MemberEntity 오류남
				.builder() // 빌드를 하고 실제 Entity 가 만들어진다.
				.email(dto.getEmail()).password(dto.getPassword()).nickname(dto.getNickname())
				.telNumber(dto.getTelNumber()).address(dto.getAddress() + " " + dto.getAddressDetail()).build();

		// JpaRepository.save(Entity) 메서드
		// 해당 Entity Id가 데이터베이스 테이블에 존재하지 않으면!
		// Entity INSERT 작업을 수행
		// 하지만!!!
		// 해당 Entity Id가 데이터베이스 테이블에 존재하면!
		// 존재하는 Entity UPDATE 작업을 수행
		memberRepository.save(member); // DB 에 save

		return ResponseDto.setSuccess("회원가입에 성공했습니다.", new ResultResponseDto(true));
	}

	public ResponseDto<GetUserResponseDto> patchUser(PatchUserDto dto) {
		// dto에서 이메일을 가져옴
		String email = dto.getEmail();

		// repository를 이용해서 데이터베이스에 있는 member 테이블 중
		// 지정한 email에 해당하는 데이터를 불러옴
		MemberEntity member = null;
		try {
			member = memberRepository.findById(email).get();
		} catch (Exception e) {
			// 만약 존재하지 않으면 FailResponse 로 "Not Exist User" 반환
			return ResponseDto.setFailed("Not Exist User");
		}

		// RequsetBody 로 받은 nickname 과 profile 로 각각 변경
		member.setNickname(dto.getNickname());
		member.setProfile(dto.getProfile());

		// 변경한 entity를 repository를 이용해서 데이터베이스에 적용(저장)
		memberRepository.save(member);
		
		// 결과를 ResponseDto에 담아서 변환
		return ResponseDto.setSuccess("User Patch Success", new GetUserResponseDto(member));
	}
	
	public ResponseDto<ResultResponseDto> deleteUser(String email){
		// Use Repository Delete that email in Member's Table at DB
		// 레포지토리를 이용하여 데이터베이스에 있는 멤버의 테이블중 email에해당하는 데이터 삭제 
		memberRepository.deleteById(email);
		
		return ResponseDto.setSuccess(email, new ResultResponseDto(true));
	}
}
