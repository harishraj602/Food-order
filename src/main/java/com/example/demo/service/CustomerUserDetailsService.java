package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.OwnException;
import com.example.demo.repository.UserRepository;


@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository urepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return urepo.findByEmail(username).orElseThrow(()->new OwnException("username not found"+username));
	}

}
