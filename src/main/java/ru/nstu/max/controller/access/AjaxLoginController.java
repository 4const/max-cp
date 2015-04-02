package ru.nstu.max.controller.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nstu.max.controller.json.LoginJson;
import ru.nstu.max.security.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class AjaxLoginController {

	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void login() {}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public LoginJson performLogin(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpServletRequest request, HttpServletResponse response) {

        return securityContext.authentificate(username, password, request, response)
			.map(ud -> new LoginJson("OK", ud.getUsername(), securityContext.isAdmin(ud)))
			.orElse(new LoginJson("Fail", null, false));
	}
}
