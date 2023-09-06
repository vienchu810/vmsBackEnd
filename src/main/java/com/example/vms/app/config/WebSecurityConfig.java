package com.example.vms.app.config;

import com.example.vms.app.security.JwtAuthenticationEntryPoint;
import com.example.vms.app.security.JwtAuthenticationFilter;
import com.example.vms.app.service.CustomUserDetailsService;
import com.example.vms.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .ignoringAntMatchers("/websocket/**", "/ws/**")
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/files/**",
                        "/**/*.mp3",
                        "/websocket/**",
                        "/public/**",
                        "/ws/**")
                .permitAll().antMatchers("/api/auth/**")
                .permitAll().antMatchers("/api/auth/register/**")
                .permitAll().antMatchers("/api/auth/login/**")
                .permitAll().antMatchers("/api/auth/logout/**")
                .permitAll().antMatchers("/api/auth/addCards/**")
                .permitAll().antMatchers("/api/getCartItems/{userId}")
                .permitAll().antMatchers("/api/delCartItems/{idProduct}")

                .permitAll().antMatchers("/api/getProducts/**")
                .permitAll().antMatchers("/api/getProductByName/{nameProduct}")
                .permitAll().antMatchers("/api/postProducts/**")
                .permitAll().antMatchers("/api/getCate/**")
                .permitAll().antMatchers("/api/addCate")
                //product
                .permitAll().antMatchers("/api/getCateProduct/**")
                .permitAll().antMatchers("/api/addCateProduct")
                .permitAll().antMatchers("/api/uploadSong")
                .permitAll().antMatchers("/api/getSong")
                .permitAll().antMatchers("/api/addSong")
                .permitAll().antMatchers("/api/download/{fileName:.+}")





                .permitAll().anyRequest()
                .authenticated();

        // Add our custom JWT security filter
       http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
