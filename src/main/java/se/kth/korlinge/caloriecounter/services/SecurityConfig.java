package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for Spring security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
              .csrf().disable()
              .authorizeRequests()
              .antMatchers("/login").permitAll()
              .antMatchers("/registration").permitAll()
              .antMatchers("/foods").authenticated()
              .antMatchers("/userdays").authenticated()
              .antMatchers("/meals").authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .usernameParameter("username")
              .passwordParameter("password")
              .and()
              .logout();

    }

    /**
     * Password encoder. Bean for singleton.
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * The authenticator for Spring to use, we want to do it via database and the loginservice
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(loginService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    /**
     * Set authenticator
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


}
