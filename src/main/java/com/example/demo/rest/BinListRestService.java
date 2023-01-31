package com.example.demo.rest;

import com.example.demo.rest.model.BinList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BinListRestService {

    public final RestTemplate binListRestTemplate;

    @Value("${rest.binlist.url}")
    private String binListUrl;

    public BinList retrieveBinList(String bin) {
        return binListRestTemplate.getForObject(binListUrl + "/{bin}", BinList.class, bin);
    }

}
