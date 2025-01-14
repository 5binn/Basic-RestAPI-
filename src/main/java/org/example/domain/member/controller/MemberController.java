package org.example.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.dto.MemberRequestDto;
import org.example.domain.member.dto.MemberResponseDto;
import org.example.domain.member.service.MemberService;
import org.example.global.resultData.ResultCode;
import org.example.global.resultData.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<ResultData<MemberResponseDto>> signup(@RequestBody @Valid MemberRequestDto.IdPwRq createRq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultData.of(ResultCode.F_06, "공백 입력"));
        }
        MemberResponseDto data = memberService.signup(createRq.getUsername(), createRq.getPassword());
        if (data == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResultData.of(ResultCode.F_05, "중복 ID 존재"));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultData.of(ResultCode.S_02, "회원가입 성공", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ResultData<MemberResponseDto>> login(@RequestBody @Valid MemberRequestDto.IdPwRq loginRq, BindingResult bindingResult,
                                                               HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultData.of(ResultCode.F_06, "입력값 오류"));
        }
        if (!memberService.loginCheck(loginRq.getUsername(), loginRq.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResultData.of(ResultCode.F_02, "아이디/비밀번호 일치하지 않음"));
        }
        memberService.login(request, response, loginRq.getUsername());
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_06, "로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResultData<MemberResponseDto>> logout(HttpServletRequest request) {
        memberService.logout(request);
        return ResponseEntity
                .ok(ResultData.of(ResultCode.S_07, "로그아웃 성공"));
    }
}
