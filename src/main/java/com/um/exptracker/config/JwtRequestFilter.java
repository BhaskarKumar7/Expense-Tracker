package com.um.exptracker.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.um.exptracker.service.impl.CustomUserDetailsService;
import com.um.exptracker.utils.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		String email = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			email = jwtUtil.extractUsername(jwt);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 System.out.println("Email extracted from JWT: " + email); // debugging
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
			if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
				 System.out.println("JWT validated for user: " + email); //debugging
				UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				upToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upToken);
			}
		} 
		else {
			System.out.println("JWT validation failed for user: " + email); // debugging
		}
		filterChain.doFilter(request, response);
	}

}
