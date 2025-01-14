package org.example.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberRequestDto {

    @Getter
    @Setter
    public static class IdPwRq {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
