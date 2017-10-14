package cn.com.jicongg.config.security;

/**
 * 安全配置.
 * 
 * 2017年9月27日
 * 
 * @author jicong.
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {}/*extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http  
	        //禁用CSRF保护  spring4默认开启.
	        .csrf().disable()  
	        .authorizeRequests()  
	        //配置那些路径可以不用权限访问   goods
	        .antMatchers("/", "/user/**").permitAll()
	        //任何访问都必须授权  
	        .anyRequest().fullyAuthenticated()  
	        .and()  
	        .formLogin();  
/*	        //登陆成功后的处理，因为是API的形式所以不用跳转页面  
	        .successHandler(new RestAuthenticationSuccessHandler())  
	        //登陆失败后的处理  
	        .failureHandler(new SimpleUrlAuthenticationFailureHandler())  
	        .and()  
	        //登出后的处理  
	        .logout().logoutSuccessHandler(new RestLogoutSuccessHandler())  
	        .and()  
	        //认证不通过后的处理  
	        .exceptionHandling()  
	        .authenticationEntryPoint(new RestAuthenticationEntryPoint()); 
	}

}*/
