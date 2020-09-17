package app.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.AddPurchaseLogic;
import app.model.Employee;
import app.model.Purchase;
import app.model.PurchaseData;
import app.model.UserData;


@Controller
public class LunchSpringPurchaseController {

	@Autowired
	UserData userData;

	@Autowired
	PurchaseData P_Data;

	@Autowired
	Employee emp;

	@Autowired
	AddPurchaseLogic addPurchaseLogic;

	@PostMapping("/purchaseCheck")
	public String purchaseCheck(Model model, @RequestParam("date") String date, @RequestParam("suu") int suu) {

		P_Data.setDate(date);
		P_Data.setSuu(suu);

		model.addAttribute("date", date);
		model.addAttribute("suu", suu);

		return "userConfirm";
	}

	@PostMapping("/purchaseRegister")
	public String purchaseRegister(Model model) {


		String id = userData.getUser_Id();
		Date date = Date.valueOf(P_Data.getDate());
		int suu = P_Data.getSuu();

		Purchase purchase = new Purchase(id, date, suu);
		boolean Purchase = addPurchaseLogic.execute(purchase);

		model.addAttribute("date", date);
		model.addAttribute("suu", suu);

		if (Purchase == true) {
			//フォワード
			return "userComplete";
		}

		return "userTop";

	}

}



