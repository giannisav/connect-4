package com.ihu.Connect_4.controllers_view;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.services.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameViewController {

    private final GameService gameService;

    public GameViewController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping({"/"})
    public String home() {
        return "/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("nickname", "");
        return "createPage";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("nickname", "");
        model.addAttribute("id","");
        return "joinPage";
    }

    @GetMapping("/games")
    public String findAvailableGames(Model model) {
        List<GameResponseDTO> games = gameService.findAvailableGames();
        model.addAttribute("games", games);
        return "joinPage";
    }

    @GetMapping("/board")
    public String getGameState(@ModelAttribute("nickname") String nickname,
                               @ModelAttribute("id") Long id,
                               Model model) {
        GameResponseDTO game = gameService.getGameStatus(nickname, id);
        model.addAttribute("game", game);
        return "board";
    }

    @PostMapping("/create")
    public String createGame(@ModelAttribute(name = "nickname") String nickname,
                             Model model) {
        GameResponseDTO game = gameService.createGame(nickname);
        model.addAttribute("game",game);
        return "board";
    }

    @PostMapping("/join")
    public String joinGame(@ModelAttribute(name = "nickname") String nickname,
                           @ModelAttribute(name = "id") String id,
                           Model model) {
        GameResponseDTO game = gameService.joinGame(nickname, Long.parseLong(id));
        model.addAttribute("game",game);
        return "board";
    }

    @PostMapping("/play")
    public String play(@ModelAttribute(name = "nickname") String nickname,
                       @ModelAttribute(name = "token") String token,
                       @ModelAttribute(name = "id") String id,
                       @ModelAttribute(name="column") String column,
                       Model model) {
        GameResponseDTO game = gameService.play(nickname, token, Long.parseLong(id), Integer.parseInt(column));
        model.addAttribute("game", game);
        return "board";
    }

    @PostMapping("/cheat")
    public String cheat(@ModelAttribute(name = "nickname") String nickname,
                        @ModelAttribute(name = "token") String token,
                        @ModelAttribute(name = "id") String id,
                        Model model) {
        GameResponseDTO game = gameService.cheatPlay(nickname, token, Long.parseLong(id));
        model.addAttribute("game", game);
        return "board";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
