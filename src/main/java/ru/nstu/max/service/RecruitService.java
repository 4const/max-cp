package ru.nstu.max.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nstu.max.dao.RecruitDAO;
import ru.nstu.max.model.Recruit;

import java.util.List;

@Service
@Transactional
public class RecruitService {

	@Autowired
	private RecruitDAO recruitDAO;

	public Recruit save(Recruit recruit) {
		return recruitDAO.save(recruit);
	}

	public List<Recruit> findAll() {
		return recruitDAO.findAll();
	}
}
