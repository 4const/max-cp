package ru.nstu.max.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ru.nstu.max.model.User;

public interface UserDAO extends Repository<User, Integer> {

	@Query("from User where login = ?1")
	User findOne(String login);
}
