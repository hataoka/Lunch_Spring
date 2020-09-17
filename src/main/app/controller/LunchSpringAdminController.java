package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.dao.UserDAO;
import app.model.GetPurchaseListLogic;
import app.model.Purchase;

@Controller
public class LunchSpringAdminController {

	@Autowired
	UserDAO dao;

	@Autowired
	GetPurchaseListLogic getPurchaseListLogic;

	@RequestMapping("/getAdminDetail")
	public String adminDetail(Model model, @RequestParam("id") String id) {

		List<Purchase> purchaseList = getPurchaseListLogic.execute(id);
		model.addAttribute("purchaseList", purchaseList);

		String Username = dao.getName(id);
		model.addAttribute("name", Username);

		//リストの合計を求めるための計算をして保存
		int total = 0;
		for (int i = 0; i < purchaseList.size(); i++) {
			Purchase plist = purchaseList.get(i);
			int instead = plist.getSuu();
			total = total + instead;
		}
		model.addAttribute("total", total);

		return "adminDetail";

	}
}
