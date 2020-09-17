package app.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
	String id;
	String name;
	int kubun;

	public User() {
	}

	public User(String id, String name, int kubun) {
		this.id = id;
		this.name = name;
		this.kubun = kubun;
	}

	public void setId(String id) {
		this.id = id;
	}
}