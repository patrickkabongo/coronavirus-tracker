package com.pandemia.coronavirustracker.services;


import com.pandemia.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/patrickkabongo/patrickkabongo.github.io/master/assets/brazil_covid19.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    public void fetchVirusData() throws IOException, InterruptedException, ParseException {

        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(VIRUS_DATA_URL))
                                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        for (CSVRecord record : records) {
                LocationStats locationStats = new LocationStats();

                locationStats.setDate(formatter.format(LocalDate.parse(record.get(record.size()-7))));
                locationStats.setState(record.get(record.size()-5));
                locationStats.setCases(Integer.parseInt(record.get(record.size()-2)));
                locationStats.setDeaths(Integer.parseInt(record.get(record.size()-1)));

                System.out.println(locationStats);
                newStats.add(locationStats);
        }

        this.allStats = newStats.stream().filter(p->p.getDate().equals("21-03-2020")).flatMap(Stream::of).collect(Collectors.toList());
    }
}







