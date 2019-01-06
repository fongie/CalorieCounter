package se.kth.korlinge.caloriecounter.services;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration class for Spring security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RESTAuthenticationEntryPoint authEntryPoint;
    @Autowired
    private RESTAuthenticationSuccessHandler authSuccessHandler;
    @Autowired
    private RESTAuthenticationFailureHandler authFailureHandler;

    /**
     * Configures Spring Security. Configured to use http basic security where user and pass are sent in every request, a method which is not very safe and should not be used in production.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
              .cors()
              .and()
              .csrf().disable()
              .authorizeRequests()
              .antMatchers("/login").permitAll()
              .antMatchers("/registration").permitAll()
              .antMatchers("/foods").authenticated()
              .antMatchers("/userdays").authenticated()
              .antMatchers("/meals").authenticated()
              .antMatchers("/loggedin").authenticated()
              .and()
              .exceptionHandling()
              .authenticationEntryPoint(authEntryPoint)
              .and()
              .formLogin()
              .successHandler(authSuccessHandler)
              .failureHandler(authFailureHandler)
              .loginPage("/login")
              .usernameParameter("username")
              .passwordParameter("password")
              .and()
              .httpBasic()
              .and()
              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

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
     * Set authenticator to our authenticator.
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //because i had problems with CORS not being allowed on dev machine..
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
              "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
