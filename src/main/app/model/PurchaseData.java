package app.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class PurchaseData {
	@Getter
	@Setter
	private String date;

	@Getter
	@Setter
	private int suu;
}
