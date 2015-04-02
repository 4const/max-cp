package ru.nstu.max.controller.json;

public class LoginJson {

	public String status;
	public String name;
	public boolean admin;

	public LoginJson(String status, String name, boolean admin) {
		this.status = status;
		this.name = name;
		this.admin = admin;
	}
}
