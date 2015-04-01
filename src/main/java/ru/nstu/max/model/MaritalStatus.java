package ru.nstu.max.model;

public enum MaritalStatus {
	SINGLE((short)1, "Холост"), MARRIED((short)2, "Женат");

	public final short id;
	public final String name;

	MaritalStatus(short id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MaritalStatus byId(short id) {
		MaritalStatus[] types = MaritalStatus.values();

		int i = id - 1;
		if (i < 0 || i > types.length) {
			return null;
		}

		return types[i];
	}
}
