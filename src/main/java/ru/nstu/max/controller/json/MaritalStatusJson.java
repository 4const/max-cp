package ru.nstu.max.controller.json;

import ru.nstu.max.model.MaritalStatus;

import java.util.function.Function;

public class MaritalStatusJson {

	public short id;
	public String name;

	public MaritalStatusJson(short id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Function<MaritalStatus, MaritalStatusJson> toJson = m -> new MaritalStatusJson(m.id, m.name);
	public static Function<MaritalStatusJson, MaritalStatus> fromJson = m -> MaritalStatus.byId(m.id);
}
