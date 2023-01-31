package com.example.demo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BinList(Number number, String scheme, String type, String brand, Bank bank) {
}
