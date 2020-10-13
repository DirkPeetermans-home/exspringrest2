package be.abis.exercise.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import be.abis.exercise.model.RateResult;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private RestTemplate rt;

	private String baseUri = "https://api.exchangeratesapi.io";

	@Override
	public double getExchangeRate(String toCur) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUri + "/latest").queryParam("symbols",
				toCur);
		ResponseEntity<RateResult> rr = rt.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, RateResult.class);
		RateResult r = rr.getBody();
		System.out.println("Date: " + r.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		double d = r.getRates().get(toCur);
		return d;
	}

}
