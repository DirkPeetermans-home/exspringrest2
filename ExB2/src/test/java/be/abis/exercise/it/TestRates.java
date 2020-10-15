package be.abis.exercise.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import be.abis.exercise.services.CurrencyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRates {
	
	@Autowired CurrencyService cs;
	
	@Test
	public void testGetRate() {
		String toCur="JPY";
		double d = cs.getExchangeRate(toCur);
		System.out.println("Rate from EUR to "+ toCur+  " is " + d);
	}
}
