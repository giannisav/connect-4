package com.ihu.Connect_4.controllers;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.exceptions.*;
import com.ihu.Connect_4.services.GameService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connect4/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/create")
    public ResponseEntity<GameResponseDTO> createGame(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok().body(gameService.createGame(nickname));
    }

    @PostMapping("/join")
    public ResponseEntity<GameResponseDTO> joinGame(@RequestParam("nickname") String nickname, @RequestParam("id") Long id) {
        return ResponseEntity.ok().body(gameService.joinGame(nickname, id));
    }

    @PostMapping("/play")
    public ResponseEntity<GameResponseDTO> playGame(@RequestParam("nickname") String nickname, @RequestParam("id") Long id,
                                                    @RequestParam("column") int column) {
        return ResponseEntity.ok(gameService.play(nickname, id, column));
    }

    @GetMapping("/game")
    public ResponseEntity<GameResponseDTO> getGame(@RequestParam("nickname") String nickname, @RequestParam("id") Long id) {
        return ResponseEntity.ok().body(gameService.getGameStatus(nickname, id));
    }

    @PostMapping("/cheat")
    public ResponseEntity<GameResponseDTO> cheat(@RequestParam("nickname") String nickname, @RequestParam("id") Long id)  {
        return ResponseEntity.ok().body(gameService.cheatPlay(nickname, id));
    }

    @ExceptionHandler({ NotExistingPlayerException.class, NotExistingGameException.class, InvalidMoveException.class,
            InvalidTurnPlayException.class, UnauthorizedPlayerException.class, FullGameException.class })
    public ResponseEntity<String> badRequestExceptionHandler(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UnavailableCheatServiceException.class)
    public ResponseEntity<String> cheatServiceExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage());
    }
}
