package com.breno.coronamonitor.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.breno.coronamonitor.models.LocationStats;

@Service // Vai ser um serviço que vai rodar quando o programa abrir
public class CoronavirusDataServices {
	
	// Constante do URL da tabela
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allStats = new ArrayList<>();
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct // Vai rodar assim que a classe for chamada
	@Scheduled(cron = "1 * * * * *") // Vai atualizar todo segundo
	public void fetchVirusData() throws IOException, InterruptedException {		

		List<LocationStats> newStats = new ArrayList<>();
		
		// Pegando as infos do link acima através do Http e fazendo um Request e Responce
		HttpClient client  = HttpClient.newHttpClient();
		HttpRequest request =  HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();		
		HttpResponse<String> httpResponse =  client.send(request, HttpResponse.BodyHandlers.ofString());
		
		// Lendo os dados da tabela pega pela resposta da requisição http 
		// Aqui tem uma biblioteca que lê CSV
		
		StringReader csvBodyReader = new StringReader(httpResponse.body()); // Precisamos criar uma string que lê a resposta HTTP
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader); // ADD a resposta 
		// Loop para ler a resposta em coluna
		for (CSVRecord record : records) {
			
			LocationStats locationStats = new LocationStats();
			
			// Add os setters
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));
			locationStats.setLatestTotalCases(Integer.parseInt(record.get((record.size() - 1))));
			
			// Add a lista
			newStats.add(locationStats);
			
		    //String state = record.get("Province/State"); // Pegamos a resposta da tabela e add ao .get todo o nome da coluna
		    //System.out.println(state); // Depois printamos 
		}
		
		this.allStats = newStats;
	}

}
