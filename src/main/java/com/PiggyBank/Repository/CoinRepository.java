package com.PiggyBank.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.PiggyBank.Model.Coin;

@Repository
public interface CoinRepository extends CrudRepository<Coin, Long>{

	public Iterable<Coin> findByValue(int value);
	
}
