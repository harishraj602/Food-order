package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomerUserDetailsService;
import com.example.demo.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtutils;
	
	@Autowired
	private CustomerUserDetailsService customeruserdetailsservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader=request.getHeader("Authorization");
		final String token;
		final String email;
		
		if(authHeader==null||authHeader.isBlank())
		{
			 filterChain.doFilter(request, response);
	            return;
		}
		
		token=authHeader.substring(7);
		email=jwtutils.extractUsername(token);
		if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails=customeruserdetailsservice.loadUserByUsername(email);
			if(jwtutils.isValidToken(token, userdetails))
			{
				SecurityContext securitycontext=SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authenticationtoken=new UsernamePasswordAuthenticationToken(userdetails,null, userdetails.getAuthorities());
				authenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securitycontext.setAuthentication(authenticationtoken);
				SecurityContextHolder.setContext(securitycontext);
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
