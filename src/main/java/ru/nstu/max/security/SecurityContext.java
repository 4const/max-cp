package ru.nstu.max.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityContext {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";


	public static Optional<UserDetails> getUserDetails()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal.equals("anonymousUser")) {
			return Optional.empty();
		}
		return Optional.of((UserDetails)principal);
	}

	public static boolean isAdmin() {
		Optional<UserDetails> detailsOp = getUserDetails();

		return detailsOp.isPresent() && isAdmin(detailsOp.get());
	}

	public static boolean isAdmin(UserDetails details) {
		return details.getAuthorities()
			.stream()
			.anyMatch(a -> a.getAuthority().equals(SecurityContext.ROLE_ADMIN));
	}
}
