package ru.nstu.max.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nstu.max.controller.json.MaritalStatusJson;
import ru.nstu.max.model.MaritalStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MaritalStatusController {

	@ResponseBody
	@RequestMapping(value = "/maritalStatus", method = RequestMethod.GET)
	public List<MaritalStatusJson> getAll() {
		return Arrays.stream(MaritalStatus.values())
			.map(MaritalStatusJson.toJson)
			.collect(Collectors.toList());
	}
}
