package org.example.global.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.domain.member.service.MemberService;
import org.example.global.resultData.ResultCode;
import org.example.global.resultData.ResultData;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Configuration
@AllArgsConstructor
public class CommonHandler {
    private final MemberService memberService;

    public <T> ResponseEntity<ResultData<T>> handleBlankErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultData.of(ResultCode.F_06, "공백 입력"));
        }
        return null;
    }

    public <T> ResponseEntity<ResultData<T>> handleNotFound(T data) {
        if (data == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResultData.of(ResultCode.F_04, "해당 데이터 없음"));
        }
        return null;
    }

    public <T> ResponseEntity<ResultData<T>> handleUnauthorized(HttpServletRequest request, String username) {
        if (!memberService.hasPermission(request, username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResultData.of(ResultCode.F_02, "권한 없음"));
        }
        return null;
    }
}
