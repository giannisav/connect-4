package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.CheatDTO;
import com.ihu.Connect_4.exceptions.InvalidTurnPlayException;
import com.ihu.Connect_4.exceptions.UnavailableCheatServiceException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CheatServiceImpl implements CheatService{

    private static final String URL = "https://connect4.gamesolver.org/solve?pos=";

    private final RestTemplate restTemplate;

    public CheatServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Integer getBestMove(String moves) {
        CheatDTO response;
        try {
            response = Optional.ofNullable(this.restTemplate.getForObject(URL + moves, CheatDTO.class))
                               .orElseThrow(() -> new UnavailableCheatServiceException("An error occurred during your attempt to cheat!"));
        }catch(Exception ex) {
            throw new UnavailableCheatServiceException("An error occurred during your attempt to cheat!");
        }
        int value = response.getScore().stream()
                .filter(x -> x < 100)
                .max(Integer::compareTo)
                .orElseThrow(() -> new InvalidTurnPlayException("GameStatus shows that you cant play!"));
        return response.getScore().indexOf(value) + 1;
    }
}
