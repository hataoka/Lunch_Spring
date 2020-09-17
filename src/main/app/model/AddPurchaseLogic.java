package app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.PurchaseDAO;

@Component
public class AddPurchaseLogic {

	@Autowired
	PurchaseDAO dao;

	public boolean execute(Purchase purchase) {
		boolean Purchase = dao.add(purchase);
		return Purchase;

	}
}
