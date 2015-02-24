package ru.nstu.max.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ru.nstu.max.model.Recruit;

import java.util.List;

public interface RecruitDAO extends Repository<Recruit, Integer> {

	List<Recruit> findAll();

	Recruit save(Recruit recruit);

	@Modifying
	@Query("delete from Recruit where id = ?1")
	void delete(Integer id);
}
