package app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.UserDAO;
import app.security.MLoginUser;

@Component
public class IdcheckLogic {

	@Autowired
	UserDAO dao;

	public MLoginUser execute(Idcheck idcheck) {
		MLoginUser User = dao.findByIdcheck(idcheck);
		return User;
	}

}
