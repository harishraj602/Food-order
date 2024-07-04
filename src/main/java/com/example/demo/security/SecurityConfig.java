package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
  @Autowired
  private CustomerUserDetailsService customUserDetailsService;
  
  @Autowired
  private JwtAuthFilter jwtauthfilter;
  
  @Bean
  public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
	 http.csrf().disable()
	 .cors(Customizer.withDefaults())
	 .authorizeHttpRequests()
	 .requestMatchers("/auth/**","/food/all").permitAll()
	 .anyRequest().authenticated()
	  .and().sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	  .authenticationProvider(authenticationProvider())
	  .addFilterBefore(jwtauthfilter,UsernamePasswordAuthenticationFilter.class);
	  return http.build();
	  
  }
  
  @Bean
  public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
      daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
      return daoAuthenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
  }
  
  
}
