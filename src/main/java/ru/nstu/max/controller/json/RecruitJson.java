package ru.nstu.max.controller.json;

import ru.nstu.max.model.MaritalStatus;
import ru.nstu.max.model.Recruit;

import java.sql.Date;
import java.util.function.Function;

public class RecruitJson {

	public Integer id;
	public String firstName;
	public String lastName;
	public String dateOfBirth;
	public String address;

	public short maritalStatusId;

	public RecruitJson() {
	}

	public RecruitJson(Integer id, String firstName, String lastName, String dateOfBirth, String address, short maritalStatusId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.maritalStatusId = maritalStatusId;
	}

	public static Function<Recruit, RecruitJson> toJson =
		r -> new RecruitJson(r.getId(), r.getFirstName(), r.getLastName(),
							r.getDateOfBirth().toString(), r.getAddress(), r.getMaritalStatus().id);

	public static Function<RecruitJson, Recruit> fromJson =
		r -> new Recruit(
			r.id, r.firstName, r.lastName, Date.valueOf(r.dateOfBirth), r.address, MaritalStatus.byId(r.maritalStatusId));
}
