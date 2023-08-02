package com.december.board.controller;

import com.december.board.model.Writing;
import com.december.board.service.WritingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/writing")
public class WritingController {
    private WritingService writingService;

    @GetMapping
    public RedirectView home(Model model) {
        RedirectView rv = new RedirectView();
        rv.setUrl("/");
        return rv;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) String sortBy,
            Model model) {
        if(query==null) query = "";

        if(page == null) page = 1L;
        if(page < 1) page = 1L;

        if(sortBy == null) sortBy = "id";

        List<Writing> writingList = writingService.getPagedSearchList(query, page-1, 10L, sortBy);

        model.addAttribute("query", query);
        model.addAttribute("writings", writingList);
        model.addAttribute("page", page);
        model.addAttribute("sortBy", sortBy);
        return "search";
    }

    @GetMapping("/{id}")
    public String getWriting(@PathVariable Long id, Model model) {
        Optional<Writing> writingOptional = writingService.getWritingByIdAndAddView(id);

        if (writingOptional.isPresent()){
            Writing writing = writingOptional.get();
            model.addAttribute("writing", writing);
            return "writing";
        }
        return "error";
    }

    @PostMapping
    public String postWriting(HttpServletRequest httpRequest, Model model) {
        String title = httpRequest.getParameter("title");
        String content = httpRequest.getParameter("content");
        String author = SecurityContextHolder.getContext().getAuthentication().getName();

        if(title == null || content == null || author == null) {
            model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
            model.addAttribute("error", "Information is not enough.");
            model.addAttribute("message", "Please fill in all the blanks.");
            return "home";
        }

        Optional<Writing> writing = writingService.postWriting(title, content, author);

        if (writing.isEmpty()) {
            model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
            model.addAttribute("error", "Author is not exist.");
            model.addAttribute("message", "Please check the author name.");
            return "error";
        }

        return "redirect:/writing/" + writing.get().getId();
    }
}
