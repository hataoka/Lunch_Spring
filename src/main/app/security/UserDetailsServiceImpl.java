package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.model.DB_Data;
import app.model.Employee;
import app.model.Idcheck;
import app.model.IdcheckLogic;
import app.model.UserData;

/**
 * UserDetailsServiceの実装クラス
 * Spring Securityでのユーザー認証に使用する
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserData userData;

	@Autowired
	DB_Data d_data;

	@Autowired
	IdcheckLogic idchecklogic;

	@Autowired
	Employee emp;

	@Override
	public UserDetails loadUserByUsername(String login_id)
			throws UsernameNotFoundException {

		// 認証を行うユーザー情報を格納する
		MLoginUser Muser = null;
		try {

			Idcheck login = new Idcheck(login_id);
			Muser = idchecklogic.execute(login);

		} catch (Exception e) {
			// 取得時にExceptionが発生した場合
			throw new UsernameNotFoundException("It can not be acquired User");
		}

		// ユーザー情報を取得できなかった場合
		if (Muser == null) {
			throw new UsernameNotFoundException("User not found for login id: " + login_id);
		}

		userData.setUser_Kubun(Muser.getMKubun());
		int kubun = Muser.getMKubun();
		String Mrole = null;
		if (kubun == 10) {
			Mrole = "USER";
		} else if (kubun == 1) {
			Mrole = "ADMIN";
		}

		userData.setUser_Id(login_id);
		// ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
		return new LoginUser(Muser, Mrole);
	}

}