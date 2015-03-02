package ru.nstu.max.service.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nstu.max.dao.UserDAO;
import ru.nstu.max.security.SecurityContext;

import java.util.Arrays;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ru.nstu.max.model.User user = userDAO.findOne(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		String role = user.isAdmin() ? SecurityContext.ROLE_ADMIN : SecurityContext.ROLE_USER;

		return new User(user.getLogin(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(role)));
	}
}
