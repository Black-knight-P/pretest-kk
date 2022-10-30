package com.pretest.dott.security.config;


import com.pretest.dott.security.CustomAuthenticationEntryPoint;
import com.pretest.dott.security.JwtAuthenticationFilter;
import com.pretest.dott.security.JwtTokenProvider;
import com.pretest.dott.security.utils.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint entryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
//                .csrf().disable()      // csrf 보안 토큰 disable
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 Stateless
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(entryPoint)
//                .accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .authorizeRequests() // 요청에 대한 사용권한 체크
//                .antMatchers(SECURITY_MEMBER_AUTH_PATTERN).authenticated()
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);
//    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(SECURITY_EXCLUDE_PATTERN);
    }

    public static final String[] SECURITY_EXCLUDE_PATTERN = {
            "/",
            "/login",
            "/register",
            "/h2-console/**"
    };

    public static final String[] SECURITY_MEMBER_AUTH_PATTERN = {
            "/logout",
            "/api/**",
    };


}
