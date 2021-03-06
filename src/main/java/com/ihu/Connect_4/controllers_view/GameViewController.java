package com.ihu.Connect_4.controllers_view;

import com.ihu.Connect_4.dtos.GameDTO;
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

    @GetMapping("/createAI")
    public String createVsAiPage(Model model) {
        model.addAttribute("nickname", "");
        return "createVsAiPage";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("nickname", "");
        model.addAttribute("id","");
        return "joinPage";
    }

    @GetMapping("/games")
    public String findAvailableGames(Model model) {
        List<GameDTO> games = gameService.findAvailableGames();
        model.addAttribute("games", games);
        return "joinPage";
    }

    @GetMapping("/board")
    public String getGameState(@ModelAttribute("nickname") String nickname,
                               @ModelAttribute("id") Long id,
                               @ModelAttribute("uuid") String uuid,
                               Model model) {
        GameDTO game = gameService.getGameStatus(nickname, uuid, id);
        model.addAttribute("game", game);
        return "board";
    }

    @PostMapping("/create")
    public String createGame(@ModelAttribute(name = "nickname") String nickname,
                             Model model) {
        GameDTO game = gameService.createGame(nickname, false);
        model.addAttribute("game",game);
        return "board";
    }

    @PostMapping("/createAI")
    public String createGameVsAi(@ModelAttribute(name = "nickname") String nickname,
                             Model model) {
        GameDTO game = gameService.createGameVsAi(nickname);
        model.addAttribute("game",game);
        return "board";
    }

    @PostMapping("/join")
    public String joinGame(@ModelAttribute(name = "nickname") String nickname,
                           @ModelAttribute(name = "id") String id,
                           Model model) {
        GameDTO game = gameService.joinGame(nickname, Long.parseLong(id));
        model.addAttribute("game",game);
        return "board";
    }

    @PostMapping("/play")
    public String play(@ModelAttribute(name = "nickname") String nickname,
                       @ModelAttribute(name = "uuid") String uuid,
                       @ModelAttribute(name = "id") String id,
                       @ModelAttribute(name = "column") String column,
                       Model model) {
        GameDTO game = gameService.play(nickname, uuid, Long.parseLong(id), Integer.parseInt(column));
        model.addAttribute("game", game);
        return "board";
    }

    @PostMapping("/playAI")
    public String playVsAi(@ModelAttribute(name = "nickname") String nickname,
                       @ModelAttribute(name = "uuid") String uuid,
                       @ModelAttribute(name = "id") String id,
                       @ModelAttribute(name = "column") String column,
                       Model model) {
        GameDTO game = gameService.playVsAi(nickname, uuid, Long.parseLong(id), Integer.parseInt(column));
        model.addAttribute("game", game);
        return "board";
    }

    @PostMapping("/cheat")
    public String cheat(@ModelAttribute(name = "nickname") String nickname,
                        @ModelAttribute(name = "uuid") String uuid,
                        @ModelAttribute(name = "id") String id,
                        Model model) {
        GameDTO game = gameService.cheatPlay(nickname, uuid, Long.parseLong(id));
        model.addAttribute("game", game);
        return "board";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
