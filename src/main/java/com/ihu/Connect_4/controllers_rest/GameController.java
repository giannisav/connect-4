package com.ihu.Connect_4.controllers_rest;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.exceptions.*;
import com.ihu.Connect_4.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/create/{nickname}")
    public ResponseEntity<GameResponseDTO> createGame(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok().body(gameService.createGame(nickname));
    }

    @PostMapping("/join/{nickname}/{id}")
    public ResponseEntity<GameResponseDTO> joinGame(@PathVariable("nickname") String nickname, @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(gameService.joinGame(nickname, id));
    }

    @PostMapping("/play/{nickname}/{uuid}/{id}/{column}")
    public ResponseEntity<GameResponseDTO> playGame(@PathVariable("nickname") String nickname, @PathVariable("uuid") String uuid,
                                                    @PathVariable("id") Long id, @PathVariable("column") int column) {
        return ResponseEntity.ok(gameService.play(nickname, uuid, id, column));
    }

    @GetMapping("/{nickname}/{id}")
    public ResponseEntity<GameResponseDTO> getGame(@PathVariable("nickname") String nickname, @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(gameService.getGameStatus(nickname, id));
    }

    @GetMapping()
    public ResponseEntity<List<GameResponseDTO>> findAvailableGames() {
        return ResponseEntity.ok(gameService.findAvailableGames());
    }

    @PostMapping("/cheat/{nickname}/{uuid}/{id}/")
    public ResponseEntity<GameResponseDTO> cheat(@PathVariable("nickname") String nickname, @PathVariable("uuid") String uuid,
                                                 @PathVariable("id") Long id)  {
        return ResponseEntity.ok().body(gameService.cheatPlay(nickname, uuid, id));
    }

    @ExceptionHandler({ NotExistingPlayerException.class, NotExistingGameException.class, InvalidMoveException.class,
            InvalidTurnPlayException.class, FullGameException.class })
    public ResponseEntity<String> badRequestExceptionHandler(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedPlayerException.class)
    public ResponseEntity<String> unauthorizedExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(UnavailableCheatServiceException.class)
    public ResponseEntity<String> cheatServiceExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage());
    }
}
