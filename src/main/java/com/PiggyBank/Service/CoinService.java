package com.PiggyBank.Service;

import com.PiggyBank.Exeption.IdNotFound;
import com.PiggyBank.Model.Coin;

public interface CoinService {

	public Iterable<Coin> getAllCoin();

	public Coin createCoin(Coin coin) throws Exception;

	public Coin getCoinById(Long id) throws IdNotFound;
	
	public Coin updateCoin(Coin coin) throws Exception;
	
	public void deleteCoin(Long id) throws IdNotFound;
	
	public Iterable<Coin> getCoinByValue(int value) throws Exception;

}
