package be.abis.exercise.model;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RateResult {
	
	private String base;
	//@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	private Map<String,Double> rates;
	
	
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Map<String, Double> getRates() {
		return rates;
	}
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	
	

}
