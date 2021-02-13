package com.ihu.Connect_4.controllers_rest;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import com.ihu.Connect_4.exceptions.XssException;
import com.ihu.Connect_4.services.PlayerService;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/{nickname}")
    public ResponseEntity<PlayerDTO> savePlayer(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok().body(playerService.savePlayer(nickname));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Slice<PlayerDTO>> getStatistics(@RequestParam(value = "sortingType", required = false) SortingType sortingType,
                                                          @RequestParam(value = "sortingOrder", required = false) SortingOrder sortingOrder,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok().body(playerService.getPlayerStatistics(sortingType, sortingOrder, page, size));
    }

    @ExceptionHandler(XssException.class)
    public ResponseEntity<String> playerBadRequestExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
