package ru.nstu.max.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nstu.max.controller.json.RecruitJson;
import ru.nstu.max.model.Recruit;
import ru.nstu.max.security.SecurityContext;
import ru.nstu.max.service.RecruitService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecruitController {

	@Autowired
	private RecruitService recruitService;

	@Autowired
	private SecurityContext securityContext;

	private Comparator<Recruit> adminComparator = (l, r) -> l.getLastName().compareTo(r.getLastName());
	private Comparator<Recruit> userComparator = (l, r) -> {
		int dateCompare = l.getDateOfBirth().compareTo(r.getDateOfBirth());
		return dateCompare == 0 ?
			adminComparator.compare(l, r) : dateCompare;
	};

	@RequestMapping(value = "/recruit", method = RequestMethod.GET)
	@ResponseBody
	public List<RecruitJson> getAll() {
		boolean isAdmin = securityContext.isAdmin();

		Comparator<Recruit> comparator = isAdmin ? adminComparator : userComparator;

		return recruitService.findAll().stream()
			.sorted(comparator)
			.map(RecruitJson.toJson)
			.collect(Collectors.toList());
	}

	@RequestMapping(value = "/recruit", method = RequestMethod.PUT)
	@ResponseBody
	public RecruitJson save(@RequestBody RecruitJson recruitJson) {
		Recruit recruit = RecruitJson.fromJson.apply(recruitJson);

		Recruit saved = recruitService.save(recruit);

		return RecruitJson.toJson.apply(saved);
	}

	@RequestMapping(value = "/recruit", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("id") Integer id) {
		recruitService.delete(id);
	}
}
