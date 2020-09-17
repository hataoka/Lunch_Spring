package app.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String user_id;
	private String user_name;
	private String kuser_id;
	private int user_kubun;

	public String getUser_Id() {
		return user_id;
	}

	public String getUser_Name() {
		return user_name;
	}

	public int getUser_Kubun() {
		return user_kubun;
	}

	public String getKUser_Id() {
		return kuser_id;
	}

	public void setUser_Id(String user_id) {
		this.user_id = user_id;
	}

	public void setUser_Name(String user_name) {
		this.user_name = user_name;
	}

	public void setUser_Kubun(int user_kubun) {
		this.user_kubun = user_kubun;
	}

	public void setKUser_Id(String kuser_id) {
		this.kuser_id = kuser_id;

	}

}
