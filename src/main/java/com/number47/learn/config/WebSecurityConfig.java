package com.number47.learn.config;

import com.number47.learn.handel.MyAuthenticationFailureHandler;
import com.number47.learn.handel.MyAuthenticationSucessHandler;
import com.number47.learn.handel.MyLogOutSuccessHandler;
import com.number47.learn.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author number47
 * @date 2019/10/13 23:25
 * @description
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private MyAuthenticationSucessHandler authenticationSuccessHandler;

	@Autowired
	private MyAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private MyLogOutSuccessHandler logOutSuccessHandler;

	@Autowired
	private UserDetailService userDetailService;

	/**
	 * 用于密码加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin() // 表单登录
		// http.httpBasic() // HTTP Basic
			.loginPage("/authentication/require") // 登录跳转 URL
			.loginProcessingUrl("/login") // 处理表单登录 URL
			.defaultSuccessUrl("/user")
			.successHandler(authenticationSuccessHandler) // 处理登录成功
			.failureHandler(authenticationFailureHandler) // 处理登录失败
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(logOutSuccessHandler)
			.deleteCookies("JSESSIONID")
		.and()
			.authorizeRequests() // 授权配置
			.antMatchers("/authentication/require", "/login.html").permitAll() // 登录跳转 URL 无需认证
			.anyRequest()  // 所有请求
			.authenticated() // 都需要认证
		.and().
			csrf().disable();


	}


	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}


}
