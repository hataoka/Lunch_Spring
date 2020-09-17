package app.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.PurchaseDAO;

@Component
public class GetPurchaseSumListLogic {

	@Autowired
	PurchaseDAO dao;

	public List<PurchaseSum> execute() {
		List<PurchaseSum> purchaseSumList = dao.getSumGroupByUser();
		return purchaseSumList;
	}
}