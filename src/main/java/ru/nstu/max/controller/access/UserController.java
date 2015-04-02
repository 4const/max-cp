package ru.nstu.max.controller.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nstu.max.controller.json.LoginJson;
import ru.nstu.max.security.SecurityContext;

import java.util.Optional;

@Controller
public class UserController {

	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public LoginJson check() {
		Optional<UserDetails> detailsOp = securityContext.getUserDetails();

		if (!detailsOp.isPresent()) {
			return null;
		}

		UserDetails ud = detailsOp.get();
		return new LoginJson("OK", ud.getUsername(), securityContext.isAdmin(ud));
	}
}
