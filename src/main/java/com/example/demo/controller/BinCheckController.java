package com.example.demo.controller;

import com.example.demo.rest.BinListRestService;
import com.example.demo.rest.model.BinList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BinCheckController {

    private final BinListRestService binListRestService;

    // sample bin: 45717360
    @GetMapping("/public/bin/{bin}")
    public ResponseEntity<BinList> getBinList(@PathVariable("bin") String bin) {
        return ResponseEntity.ok().body(binListRestService.retrieveBinList(bin));
    }

}
