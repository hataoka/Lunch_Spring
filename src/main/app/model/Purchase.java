package app.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class Purchase implements Serializable {
	private String id;
	private Date purchase_date;
	private int suu;

	public Purchase() {
	}

	public Purchase(String id, Date purchase_date, int suu) {
		this.id = id;
		this.purchase_date = purchase_date;
		this.suu = suu;
	}

}