package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.kth.korlinge.caloriecounter.services.LoginService;

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
              .and()
              .formLogin()
              .loginPage("/login")
              .usernameParameter("username")
              .passwordParameter("password")
              .and()
              .logout();

    }

    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                 //orders
                .antMatchers("/orders/setbackupemail/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/orders/getbackupemail").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PATCH, "/orders/{\\d+}").access("hasRole('ROLE_COURIER') or hasRole('ROLE_DISPATCH')")
                .antMatchers("/orders/courier/**").access("hasRole('ROLE_COURIER') or hasRole('ROLE_DISPATCH')")
                .antMatchers("/orders/**").access("hasRole('ROLE_DISPATCH')")
                // /companies
                .antMatchers("/companies/**").access("hasRole('ROLE_DISPATCH')")
                // /persons
                //.antMatchers("/persons/**").access("hasRole('ROLE_DISPATCH')")
                // /customers
                .antMatchers("/customers/**").access("hasRole('ROLE_DISPATCH')")
                // /addresses
                .antMatchers("/addresses/**").access("hasRole('ROLE_DISPATCH')")
                // /users
                .antMatchers("/users").access("hasRole('ROLE_DISPATCH')")
                // /roles
                .antMatchers("/roles").access("hasRole('ROLE_DISPATCH') or hasRole('ROLE_COURIER')")
                // /registration
                //.antMatchers("/registration").access("hasRole('ROLE_ADMIN')")
                // /reports
                .antMatchers("/reports").access("hasRole('ROLE_ACCOUNT_MANAGER')")
                //login
                .antMatchers("/login").permitAll()
                .and().csrf().disable().formLogin().failureHandler(authenticationFailureHandler)
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
    */

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
