package com.example.fabrickdemo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.fabrickdemo.model.BankAccountTransaction;
import com.example.fabrickdemo.model.BankAccoutBalanceResponse;

@RestController
public class BankController {
	
	@Value("${BaseUrl}")
	private String baseUrl;
	@Value("${ApiKey}")
	private String apiKey;	
	@Value("${AuthSchema}")
	private String authSchema;	
	@Value("${IdChiave}")
	private String idChiave;
	@Value("${AccountId}")
	private String accountId;
	
	@Autowired
	private BankService bankService;

	@RequestMapping("/balance")
	public ResponseEntity<String> getBankAccountBalanceTest() {	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		
		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/balance")   
			    .build(accountId);
		
		try {
			responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.GET, new HttpEntity<String>(getHttpHeaders()), String.class);
		} catch (HttpStatusCodeException e) {
			return generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
	}
		
	@RequestMapping("/payment")
	public ResponseEntity<String> doBankAccountTransactionTest() {		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		
		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers")
			    .build(accountId);
		
		try {
			// read the body for payment transaction from resources folder
			File resource = new ClassPathResource("payment.json").getFile();
			String text = new String(Files.readAllBytes(resource.toPath()));
			responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.POST, new HttpEntity<String>(text, getHttpHeaders()), String.class);
		} catch (IOException e) {
			return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		catch (HttpStatusCodeException e) {
			return generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
	}
	
	@RequestMapping("/transactions")
	public ResponseEntity<String> getAccountTransactionsTest() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
        String fromAccountingDate = "2019-01-01";
        String toAccountingDate = "2019-12-01";
		
		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
			    .queryParam("fromAccountingDate", fromAccountingDate)
			    .queryParam("toAccountingDate", toAccountingDate) 
			    .build(accountId);
		
		try {
			responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.GET, new HttpEntity<String>(getHttpHeaders()), String.class);
		} catch (HttpStatusCodeException e) {
			return generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
	}	
	
	@RequestMapping("/accounts/{id}/balance")
	public ResponseEntity<String> getBankAccountBalance(@PathVariable String id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<BankAccoutBalanceResponse> responseEntity = null;

		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/balance")   
			    .build(accountId);		
		
		try {						
		    responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.GET, new HttpEntity<String>(getHttpHeaders()), BankAccoutBalanceResponse.class);
		} catch (HttpStatusCodeException e) {
			return generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Balance: " + responseEntity.getBody().getPayload().getBalance());
	}

	@RequestMapping(value="/accounts/{id}/payments/money-transfers", method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> doBankAccountTransaction(@RequestBody BankAccountTransaction accountTransaction) {
		boolean transferDone = bankService.doBankAccountTransaction(accountTransaction);

	    if (!transferDone) {
	    	// ...
	    }

	    // TODO
	    
	    return ResponseEntity.status(HttpStatus.OK).body("Payment done: ");		
	}	
	
	@RequestMapping(value = "/accounts/{id}/transactions", method = RequestMethod.GET)
	public ResponseEntity<String> getAccountTransactions(@PathVariable String id, @RequestParam Map<String,String> requestParams) {
		String fromAccountingDate = requestParams.get("fromAccountingDate");
		String toAccountingDate = requestParams.get("toAccountingDate");
		
		bankService.getAccountTransactions(Long.valueOf(id), fromAccountingDate, toAccountingDate);
		// TODO
		
		return ResponseEntity.status(HttpStatus.OK).body("Payment done: ");	
	}
	
	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Auth-Schema", authSchema);
	    headers.set("Api-Key", apiKey);
		return headers;
	}
	
	private ResponseEntity<String> generateResponseEntity(HttpStatus statusCode, String body) {
		if(statusCode.is4xxClientError()) {
			if(statusCode.equals(HttpStatus.BAD_REQUEST))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			else if(statusCode.equals(HttpStatus.NOT_FOUND))
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
			// other 4xx cases
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}	
}
