package com.mustache.bbs2.controller;

import com.mustache.bbs2.domain.dto.ArticleDto;
import com.mustache.bbs2.domain.entity.ArticleEntity;
import com.mustache.bbs2.domain.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/repeat_articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("")
    public String listPage(Model model) {
        List<ArticleEntity> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "list";
    }

    @GetMapping("/new")
    public String createArticlePage() {
        return "new";
    }


    @GetMapping("/{id}")
    public String showSingle(@PathVariable Long id, Model model) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (!optionalArticle.isEmpty()) {
            model.addAttribute("article", optionalArticle.get());
            return "show";
        } else
            return "error";
    }

    @PostMapping("/posts")
    public String createArticle(ArticleDto articleDto) {

        ArticleEntity savedArticle = articleRepository.save(articleDto.toEntity());
        return String.format("redirect:/repeat_articles/%d", savedArticle.getId());

    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto articleDto, Model model) {
        log.info("title:{} contents:{}", articleDto.getTitle(), articleDto.getContent());
        ArticleEntity article = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/repeat_articles/%d", article.getId());
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);

        if (!optionalArticle.isEmpty()){
            model.addAttribute("article", optionalArticle.get());
            return "/edit";
        }else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "/error";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return "redirect:/repeat_articles";
    }
}
