package app.model;

import org.springframework.stereotype.Component;

@Component
public class DB_Data {

	private String url;

	private String username;

	private String pass;

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPass(String pass) {
		this.pass = pass;

	}
}
