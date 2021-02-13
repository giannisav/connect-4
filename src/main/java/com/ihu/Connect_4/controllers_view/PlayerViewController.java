package com.ihu.Connect_4.controllers_view;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import com.ihu.Connect_4.services.PlayerService;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlayerViewController {

    private final PlayerService playerService;

    public PlayerViewController(PlayerService playerService) {
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
                                 @RequestParam(value = "sortingOrder", required = false) SortingOrder sortingOrder,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "size", required = false) Integer size) {
        Slice<PlayerDTO> slice = playerService.getPlayerStatistics(sortingType, sortingOrder, page, size);
        List<String> sortInfo = Arrays.asList(slice.getSort().toString().split(": "));
        model.addAttribute("slice", slice);
        model.addAttribute("type", sortInfo.get(0));
        model.addAttribute("order", sortInfo.get(1));
        return "statistics";
    }

    @PostMapping("/register")
    public String registerPlayer(@ModelAttribute(name = "nickname") String nickname) {
        playerService.savePlayer(nickname);
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
