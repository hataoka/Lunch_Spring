package app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.UserDAO;

@Component
public class LoginLogic {

	@Autowired
	UserDAO dao;

	public User execute(Login login) {
		User User = dao.findByLogin(login);
		return User;
	}

}
