package com.number47.learn.config;

import com.number47.learn.common.Constant;
import com.number47.learn.demo.filter.ValidateCodeFilter;
import com.number47.learn.demo.handel.MyAuthenticationFailureHandler;
import com.number47.learn.demo.handel.MyAuthenticationSucessHandler;
import com.number47.learn.demo.handel.MyLogOutSuccessHandler;
import com.number47.learn.demo.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

	@Autowired
	private ValidateCodeFilter validateCodeFilter;

	@Autowired
	private DataSource dataSource;

	/**
	 * 用于密码加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		jdbcTokenRepository.setCreateTableOnStartup(false);
		return jdbcTokenRepository;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//将验证码过滤器添加到添加到UsernamePasswordAuthenticationFilter之前
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin() // 表单登录
		// http.httpBasic() // HTTP Basic
			.loginPage("/authentication/require") // 登录跳转 URL
			.loginProcessingUrl("/login") // 处理表单登录 URL
			.defaultSuccessUrl("/user")
			.successHandler(authenticationSuccessHandler) // 处理登录成功
			.failureHandler(authenticationFailureHandler) // 处理登录失败
		.and()
			.rememberMe()
			.tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
			.tokenValiditySeconds(Constant.REMEMBER_ME_TIMES) // remember 过期时间，单为秒
			.userDetailsService(userDetailService) // 处理自动登录逻辑
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(logOutSuccessHandler)
			.deleteCookies("JSESSIONID")
		.and()
			.authorizeRequests() // 授权配置
			.antMatchers("/authentication/require", "/login.html","/code/image").permitAll() //无需认证的请求路径
			.anyRequest()  // 所有请求
			.authenticated() // 都需要认证
		.and().
			csrf().disable();


	}


	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}


}
