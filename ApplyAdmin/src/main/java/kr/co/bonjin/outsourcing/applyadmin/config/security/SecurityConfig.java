package kr.co.bonjin.outsourcing.applyadmin.config.security;

import kr.co.bonjin.outsourcing.applyadmin.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @EnableWebSecurity 스프링 시큐리티를 사용
 * WebSecurityConfigurerAdapter 를 확장하여 사용자 정의 보안 구성을 제공
 *
 * Security Exception 종류
 *
 * - AuthenticationException
 *      UsernameNotFoundException : 계정 없음
 *      BadCredentialsException : 잘못된 자격 증명
 *      AuthenticationCredentialsNotFoundException : 인증 자격 증명 없음
 *      PreAuthenticatedCredentialsNotFoundException : 사전 인증 된 자격 증명을 찾을 수 없음
 *      AccountStatusException
 *          AccountExpiredException : 계정만료
 *          CredentialsExpiredException : 비밀번호 만료
 *          DisabledException : 계정 비활성화
 *          LockedException : 계정잠기
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FailureHandler failureHandler;
    private final SuccessHandler successHandler;

    /**
     * 요청 URI 에 대한 권한 설정, 특정 기능 결과에 대한 Handler 등록,
     * Custom Filter 등록(ex. AuthenticationFilter 재정의)
     * 그리고 예외 핸들러 등을 등록 하는 메소드
     *
     * WebSecurityConfig#configure(HttpSecurity http)메소드 내부에서 보호 / 비보호 API 엔드 포인트를 정의하기 위해 패턴을 구성
     * 쿠키를 사용하지 않기 때문에 CSRF 보호를 비활성화
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        // 동일사용자 여러 동시 세션 허용
        http.sessionManagement()
                .maximumSessions(10);

        // 접근 관리 시작
        http.authorizeRequests()
                // ROLE 권한 분리 URL 정의
                .antMatchers("/api/**").permitAll()	// API 요청 전체 공개
                .antMatchers("/member/**").permitAll() // MEMBER 요청 전체 공개
                .antMatchers("/**").authenticated()   // 모든 요청에 대해 인증을 요구하는 규칙

                // BasicAuth 사용하여 로그인 요구
//                .and().httpBasic()  // Confirm 화면

                // Header
                .and()
                    .headers()
                    // Default X-Frame-Options deny : iFrame Cross Origin Error
                    .frameOptions()
                    .sameOrigin()	// 동일 도메인은 허용
//                      .disable()	// 비활성화

                // Login
                .and()
                    .formLogin()
                    .loginPage("/member/login")
                    .usernameParameter(Constants.USERNAME)
                    .passwordParameter(Constants.PASSWORD)
//                        .loginProcessingUrl("/authenticate") // 인증을 처리하는 경로
//                        .failureUrl("/login/error")
                    .defaultSuccessUrl("/")    // 직접 로그인시 홈으로 고정
                    .failureHandler(failureHandler)
                    .successHandler(successHandler)
                    .permitAll()

                // Logout
                .and()
                    .logout()
                    .logoutUrl("/member/logout")
                    .logoutSuccessUrl("/member/login")  // 로그아웃 성공시 메인 고정
                    .invalidateHttpSession(true)    // session invalidate
                    .deleteCookies("JSESSIONID")	// cookie
                    .permitAll()

                // csrf 사용유무 설정 - 사용하지 않음
                // 모든 요청에 csrf 값 전달
                .and()
//                	.addFilterBefore(filter, CsrfFilter.class)	// 문자 인코딩 filter보다 Csrf Filter를 먼저 실행
                .csrf().disable()
        ;
    }

    /*
     * 스프링 시큐리티 룰을 무시하게 하는 Url 규칙.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/images/**")
                .antMatchers("/vendors/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
        ;
    }
}
