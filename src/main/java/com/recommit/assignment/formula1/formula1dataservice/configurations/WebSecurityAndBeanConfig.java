package com.recommit.assignment.formula1.formula1dataservice.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommit.assignment.formula1.formula1dataservice.security.filters.UserAuthorizationTokenFilter;
import com.recommit.assignment.formula1.formula1dataservice.security.handler.JwtAuthenticationExceptionHandler;
import com.recommit.assignment.formula1.formula1dataservice.security.handler.UserAccessDeniedHandler;
import com.recommit.assignment.formula1.formula1dataservice.security.handler.UserLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.time.Duration;
import java.util.Locale;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityAndBeanConfig {
    private static final String[] AUTH_WHITELIST = {
            "/api-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/api/v1/auth/**",
            "/h2-admin/**"
    };
    private final UserLogoutHandler userLogoutHandler;
    private final UserAccessDeniedHandler accessDeniedHandler;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated();
        httpSecurity.formLogin().disable();
        httpSecurity.logout()
                .addLogoutHandler(userLogoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationExceptionHandler).accessDeniedHandler(accessDeniedHandler);
        httpSecurity.addFilterBefore(new UserAuthorizationTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
        // H2 console refuse connect fix: Refused to connect in a frame because it set 'X-Frame-Options' to 'deny'
        httpSecurity.headers().frameOptions().sameOrigin();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Locale Resolver
     * Header will have a prop name Accept-Language
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.US);
        return acceptHeaderLocaleResolver;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
