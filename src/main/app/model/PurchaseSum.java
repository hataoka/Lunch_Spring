package app.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PurchaseSum implements Serializable {
	private String id;
	private int suu;

	public PurchaseSum() {
	}

	public PurchaseSum(String id, int suu) {
		this.id = id;
		this.suu = suu;
	}
}