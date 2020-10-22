package com.PiggyBank.Controller;



import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.PiggyBank.Model.Coin;
import com.PiggyBank.Service.CoinService;

@Controller
public class PiggyBankController {

	@Autowired
	CoinService coinService;
	
	
	@GetMapping({"/index"})
	public String index(Model model)  {
		model.addAttribute("totalCoin",getTotalCoins());
		model.addAttribute("totalMoney",getTotalMoney());
		model.addAttribute("totalCoinsDenomination50",getTotalCoinsDenomination(50));
		model.addAttribute("totalCoinsDenominationMoney50",getTotalCoinsDenominationMoney(50));
		model.addAttribute("totalCoinsDenomination100",getTotalCoinsDenomination(100));
		model.addAttribute("totalCoinsDenominationMoney100",getTotalCoinsDenominationMoney(100));
		model.addAttribute("totalCoinsDenomination200",getTotalCoinsDenomination(200));
		model.addAttribute("totalCoinsDenominationMoney200",getTotalCoinsDenominationMoney(200));
		model.addAttribute("totalCoinsDenomination500",getTotalCoinsDenomination(500));
		model.addAttribute("totalCoinsDenominationMoney500",getTotalCoinsDenominationMoney(500));
		model.addAttribute("totalCoinsDenomination1000",getTotalCoinsDenomination(1000));
		model.addAttribute("totalCoinsDenominationMoney1000",getTotalCoinsDenominationMoney(1000));
		return "index";
	}


	@GetMapping({"/listCoin"})
	public String listCoin(Model model) {
		Iterable<Coin>  coins = coinService.getAllCoin();
		model.addAttribute("coins", coins);
		return "coin-list";
	}
	
	@GetMapping({"/addCoin"})
	public String addCoin(Model model) {
		model.addAttribute("coinForm", new Coin());
		return "coin-add";
	}
	
	
	@PostMapping("/addCoin")
	public String addCoinAction(@ModelAttribute("coinForm")Coin coin, BindingResult result, ModelMap model, Model model2) {
		try {
			coinService.createCoin(coin);
		}catch (Exception e) {
			model.addAttribute("formErrorMessage",e.getMessage());
			return "coin-add";	
		}
		return listCoin(model2);
	}
	
	
	private int getTotalCoins(){
		return (int) StreamSupport.stream(coinService.getAllCoin().spliterator(), false).count();
	}
	
	private int getTotalMoney(){	
		return StreamSupport.stream(coinService.getAllCoin().spliterator(), false).mapToInt(total -> total.getValue()).sum();
	}
	
	private int getTotalCoinsDenomination(int denomination){
		
		try {
			return (int) StreamSupport.stream(coinService.getCoinByValue(denomination).spliterator(), false).count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getTotalCoinsDenominationMoney(int denomination){
		try {
			return StreamSupport.stream(coinService.getCoinByValue(denomination).spliterator(), false).mapToInt(total -> total.getValue()).sum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	 
}
