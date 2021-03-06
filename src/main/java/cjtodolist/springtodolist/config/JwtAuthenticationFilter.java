package cjtodolist.springtodolist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
// 요청을 가로채서 토큰의 유효성 판단하는 클래스 => 유효성 확인 시 요청 진행
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 jwt 받아옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한지 확인
        if(token != null && jwtTokenProvider.validateToken(token)) {
            // 유효성 확인 후 토큰에서 유저정보 가져오기
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
