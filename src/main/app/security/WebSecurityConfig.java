package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvcSecurity
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {

		web
				.debug(false)
				.ignoring()
				.antMatchers("/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//認可の設定
		http.authorizeRequests()
				.mvcMatchers("/loginForm", "/", "/loginError").permitAll()
				.mvcMatchers("/userTop", "/userConfirm", "/userComplete").hasRole("USER")
				.mvcMatchers("/adminTop", "/adminDetail").hasRole("ADMIN")
				.anyRequest().authenticated();
		http.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/loginForm")
				.failureUrl("/loginError")
				//.failureHandler(new SampleAuthenticationFailureHandler())       // 認証失敗時に呼ばれるハンドラクラス
				.defaultSuccessUrl("/loginSuccess")
				.usernameParameter("id").passwordParameter("pass")
				.and();

		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウト処理のパス
				.logoutSuccessUrl("/index");
	}

	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		UserDetailsServiceImpl userDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// 認証するユーザーを設定する
			auth.userDetailsService(userDetailsService)
					// 入力値をbcryptでハッシュ化した値でパスワード認証を行う
					.passwordEncoder(new BCryptPasswordEncoder());

		}
	}

}
