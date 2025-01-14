package org.example.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.dto.MemberResponseDto;
import org.example.domain.member.entity.Member;
import org.example.domain.member.repository.MemberRepository;
import org.example.global.security.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 주어진 username 에 해당하는 Member 반환, 없으면 null
    public MemberResponseDto findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member
                .map(MemberResponseDto::new)
                .orElse(null);
    }

    // 회원가입, 중복된 username 있으면 null
    public MemberResponseDto signup(String username, String password) {
        if (findByUsername(username) != null) {
            return null;
        }
        Member member = Member.builder()
                .username(username)
                .password(SecurityConfig.PasswordEncoder.encodePassword(password))
                .build();
        this.memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    //로그인 검증
    public boolean loginCheck(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.isPresent()
                && SecurityConfig.PasswordEncoder.checkPassword(password, optionalMember.get().getPassword());
    }

    //세션 추가
    public void login(HttpServletRequest request, HttpServletResponse response, String username) {
        request.getSession().setAttribute("username", username);
    }

    //세션 삭제
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    //로그인한 member 가져오기
    public Member getMember(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        if (username != null) {
            return memberRepository.findByUsername((String) username).orElse(null);
        }
        return null;
    }


}
