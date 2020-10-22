package com.PiggyBank.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PiggyBank.Exeption.IdNotFound;
import com.PiggyBank.Exeption.ValueNotPermitted;
import com.PiggyBank.Model.Coin;
import com.PiggyBank.Repository.CoinRepository;

@Service
public class CoinServiceImpl implements CoinService {

	@Autowired
	CoinRepository repository;
	
	
	@Override
	public Iterable<Coin> getAllCoin() {
		return repository.findAll();
	}
	
	private boolean checkValidValueCoin(Coin coin) throws Exception {
		
		if(coin.getValue() != 50 && coin.getValue() != 100 && coin.getValue() != 200 && coin.getValue() != 500 && coin.getValue() != 1000){
			throw new ValueNotPermitted("Valor de moneda no permitido !!");
		}
		
		return true;
	}
	
	
	@Override
	public Coin createCoin(Coin coin) throws Exception {
		if (checkValidValueCoin(coin) ) {
			LocalDate date = LocalDate.now();
			coin.setFecha(date);
			coin = repository.save(coin);
		}
		return coin;
	}

	@Override
	public Coin getCoinById(Long id) throws IdNotFound {
		return repository.findById(id).orElseThrow(() -> new IdNotFound("El Id no existe."));
	}
	
	
	@Override
	public Coin updateCoin(Coin coin) throws Exception {
		
		Coin toCoin = getCoinById(coin.getId());
		mapCoin(coin, toCoin);
		return  repository.save(toCoin);
	}
	

	protected void mapCoin(Coin from,Coin to) {	
		to.setValue(from.getValue());
	}

	@Override
	public void deleteCoin(Long id) throws IdNotFound {
		Coin coin = getCoinById(id);
		repository.delete(coin);
	}
	
	@Override
	public  Iterable<Coin> getCoinByValue(int value) throws Exception {
		return repository.findByValue(value);
	}

	

	
}
