package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseCode;
import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseDTO;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@RestController
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;

    @PostMapping(value = "/api/join",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalCommonResponseDTO>postMemberByRequest
                                        (@RequestBody @Valid MemberRequestDTO request, BindingResult bindingResult){
            // 유효성 검사 실패 시 에러 응답 생성
            if (bindingResult.hasErrors()) {
                HashMap<String, String> errors = memberService.validateHandling(bindingResult);
                GlobalCommonResponseDTO errorResponse = GlobalCommonResponseDTO.builder()
                        .code(GlobalCommonResponseCode.BAD_REQUEST.getCode())
                        .message("validation error")
                        .data(errors)
                        .build();
                return ResponseEntity.badRequest().body(errorResponse);
            }else{
                MemberResponseDTO response = memberService.createMemberByRequest(request);
                GlobalCommonResponseDTO successResponse = GlobalCommonResponseDTO.builder()
                        .code(GlobalCommonResponseCode.SUCCESS.getCode())
                        .message("join success")
                        .data(response)
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }
    }


    @PutMapping(value = "/api/mypage/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalCommonResponseDTO> updateMemberInfo(@PathVariable Long id,
                                            @RequestBody @Valid MemberRequestDTO request,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            HashMap<String, String> errors = memberService.validateHandling(bindingResult);
            GlobalCommonResponseDTO errorResponse = GlobalCommonResponseDTO.builder()
                    .code(GlobalCommonResponseCode.BAD_REQUEST.getCode())
                    .message("validation error")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }else{
            MemberResponseDTO updateMemberByRequest = memberService.updateMemberByRequest(id, request);
            GlobalCommonResponseDTO successResponse = GlobalCommonResponseDTO.builder()
                    .code(GlobalCommonResponseCode.SUCCESS.getCode())
                    .message("modify success")
                    .data(updateMemberByRequest)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        }
    }

    @DeleteMapping("/api/mypage/{id}")
    public ResponseEntity deleteMemberByRequest(@PathVariable Long id){
        memberService.deleteMemberById(id);
        return ResponseEntity.ok().build();
    }




}
