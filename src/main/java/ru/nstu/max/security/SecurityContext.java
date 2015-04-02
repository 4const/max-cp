package ru.nstu.max.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class SecurityContext {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	SecurityContextRepository repository;

	public Optional<UserDetails> getUserDetails()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal.equals("anonymousUser")) {
			return Optional.empty();
		}
		return Optional.of((UserDetails)principal);
	}

	public boolean isAdmin() {
		Optional<UserDetails> detailsOp = getUserDetails();

		return detailsOp.isPresent() && isAdmin(detailsOp.get());
	}

	public boolean isAdmin(UserDetails details) {
		return details.getAuthorities()
			.stream()
			.anyMatch(a -> a.getAuthority().equals(SecurityContext.ROLE_ADMIN));
	}

	public Optional<UserDetails> authentificate(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			repository.saveContext(SecurityContextHolder.getContext(), request, response);

			return getUserDetails();
		} catch (BadCredentialsException e) {
			return Optional.empty();
		}
	}
}
