package com.december.board.controller;

import com.december.board.model.Writing;
import com.december.board.service.WritingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeContorller {
    private WritingService writingService;

    @RequestMapping("/")
    public String home(@RequestParam(required = false) Long page, @RequestParam(required = false) String sortBy, Model model) {
        if (page == null) page = 1L;
        if(page < 1) page = 1L;

        if(sortBy == null) sortBy = "id";

        List<Writing> writingList = writingService.getPagedList(page-1, 10L, sortBy);

        if(writingList.size() == 0){
            return "error";
        }


        model.addAttribute("writingList", writingList);
        model.addAttribute("page", page);
        model.addAttribute("sortBy", sortBy);
        return "home";
    }

    @GetMapping("/post")
    public String post(Model model) {
        String authorName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("authorName", authorName);

        return "post";
    }
}
