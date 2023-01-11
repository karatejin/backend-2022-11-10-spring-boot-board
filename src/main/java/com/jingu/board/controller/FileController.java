package com.jingu.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jingu.board.service.FileService;

@RestController
@RequestMapping("file/")
public class FileController {
	
	@Autowired FileService fileService;
	
	// 파일을 서버에 업로드. Upload file to Server 서버로 가는거다. post
	@PostMapping("upload") 
	//post는 requestbody 에서 받는다.전체로 받을때 body 분리는 param
	//@RequestParm(file명) : RequestBody에서 특정 필드를 받아옴. 파일을 MultipartFile 받는다.
	// Request body 에 파일을 받아 올땐 MultiparFile 인스턴스로 받음
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		return fileService.fileUpload(file);	
	}
	
	// 파일을 서버에서 다운로드 Download File from Server 서버에서 가져오는거다 Get
	@GetMapping("download/{fileName}")
	//ResponseEntity 컨트롤 해준다.
	public ResponseEntity<Resource> fileDownload(@PathVariable("fileName") String fileName){
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.body(fileService.fileDownload(fileName));
	}
	
	// 이미지파일일 경우 이미지를 출력 if file is ImgFile printout image in Screen	
	@GetMapping(value = "image/{imageName}", produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
	public Resource getImage(@PathVariable("imageName")String imageName) {
		return fileService.getImage(imageName);
	}
	
	
}
