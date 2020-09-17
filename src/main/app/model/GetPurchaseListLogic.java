package app.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.PurchaseDAO;

@Component
public class GetPurchaseListLogic {

	@Autowired
	PurchaseDAO dao;

	public List<Purchase> execute(String id) {
		List<Purchase> purchaseList = dao.findById(id);
		return purchaseList;
	}

	public List<Purchase> execute(String id, String date) {
		List<Purchase> purchaseList = dao.findByDate(id, date);
		return purchaseList;
	}

}
