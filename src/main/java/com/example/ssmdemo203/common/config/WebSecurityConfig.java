package com.example.ssmdemo203.common.config;

import com.example.ssmdemo203.common.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeinedHanler jwtAccessDeinedHanler;

    @Resource
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception{
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            //????????????????????????
            .successHandler(jwtAuthenticationSuccessHandler)
            //????????????????????????
            .failureHandler(loginFailureHandler)
            //???????????????????????????
            .loginProcessingUrl("/login")
            .and()
            //????????????????????????
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            //??????????????????????????????
            .accessDeniedHandler(jwtAccessDeinedHanler)
            .and()
            //???????????????????????????session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated();

        //????????????
        http.cors().and().csrf().disable();

        //??????jwt???????????????
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        //??????
        http.headers().cacheControl();
    }
}