package com.ihu.Connect_4.controllers_view;


import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.services.GameService;
import com.ihu.Connect_4.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class GameViewController {

    private final GameService gameService;
    private final PlayerService playerService;

    public GameViewController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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

    @GetMapping("/board/{nickname}/{id}")
    public String getGameState(@PathVariable("nickname") String nickname,
                               @PathVariable("id") Long id, Model model) {
        GameResponseDTO game = gameService.getGameStatus(nickname, id);
        model.addAttribute("game", game);
        return "/board";
    }

    @PostMapping("/create")
    public String createGame(@ModelAttribute(name = "nickname") String nickname) {
        GameResponseDTO game = gameService.createGame(nickname);
        return "redirect:/board/" + nickname + "/" + game.getId();
    }

    @PostMapping("/join")
    public String joinGame(@ModelAttribute(name = "nickname") String nickname,
                           @ModelAttribute(name = "id") String id) {
        GameResponseDTO game = gameService.joinGame(nickname, Long.parseLong(id));
        return "redirect:/board/" + game.getRedPlayerNickname() + "/" + game.getId();
    }

    @PostMapping("/play")
    public String play(@ModelAttribute(name = "nickname") String nickname,
                       @ModelAttribute(name = "token") String token,
                       @ModelAttribute(name = "id") String id,
                       @ModelAttribute(name="column") String column) {
        gameService.play(nickname, token, Long.parseLong(id), Integer.parseInt(column));
        return "redirect:/board/" + nickname + "/" + id;
    }

    @PostMapping("/cheat")
    public String cheat(@ModelAttribute(name = "nickname") String nickname,
                        @ModelAttribute(name = "token") String token,
                        @ModelAttribute(name = "id") String id) {
        gameService.cheatPlay(nickname, token, Long.parseLong(id));
        return "redirect:/board/" + nickname + "/" + id;
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("url", request.getRequestURL());
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }


}
