package com.ihu.Connect_4.controllers_view;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import com.ihu.Connect_4.services.GameService;
import com.ihu.Connect_4.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlayerViewController {


    private final PlayerService playerService;

    public PlayerViewController(GameService gameService, PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("nickname", "");
        return "registerPage";
    }

    @GetMapping("/statistics")
    public String findStatistics(Model model,
                                 @RequestParam(value = "sortingType", required = false) SortingType sortingType,
                                 @RequestParam(value = "sortingOrder", required = false) SortingOrder sortingOrder) {
        List<PlayerDTO> players = playerService.getPlayerStatistics(sortingType, sortingOrder);
        model.addAttribute("players", players);
        return "/statistics";
    }

    @PostMapping("/register")
    public String registerPlayer(@ModelAttribute(name = "nickname") String nickname) {
        playerService.savePlayer(nickname);
        return "redirect:/";
    }
}
