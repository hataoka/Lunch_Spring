package app.security;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Entity
@Component
public class MLoginUser implements java.io.Serializable {
	@Id
	private String loginUserId = null;
	private String password = null;
	private int kubun = 0;

	public MLoginUser() {
	}

	public MLoginUser(String id, String pass, int kubun) {

		this.loginUserId = id;
		//取得したパスワードをエンコードした後に代入
		String epass = new BCryptPasswordEncoder().encode(pass);
		this.password = epass;
		this.kubun = kubun;
	}

	public String getLoginUserId() {
		return this.loginUserId;
	}

	public String getPassword() {
		return this.password;
	}

	public int getMKubun() {
		return this.kubun;
	}

}