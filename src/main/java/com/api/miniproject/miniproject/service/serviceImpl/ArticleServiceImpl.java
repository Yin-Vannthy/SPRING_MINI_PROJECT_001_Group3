package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.configuration.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Category;
import com.api.miniproject.miniproject.model.entity.CategoryArticle;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.ArticleRequest;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.repository.ArticleRepository;
import com.api.miniproject.miniproject.repository.CategoryArticleRepository;
import com.api.miniproject.miniproject.repository.CategoryRepository;
import com.api.miniproject.miniproject.service.ArticleService;
import com.api.miniproject.miniproject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    private final CommentService commentService;

    private AppUser currentUser() {
        return CurrentUser.getCurrentUser();
    }

    @Override
    public List<ArticleDto> findArticlesByCategoryCategoryId(Long categoryId) {
        return articleRepository.findArticlesByCategoryCategoryId(categoryId)
                .stream()
                .map(Article::toArticleResponseWithRelatedData)
                .collect(Collectors.toList());
    }

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findByCategoryIdAndUserUserId(categoryId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No category with Id : " + categoryId + " was found for this current user."));
    }

    private void updateCategoryArticleCount(Category category) {
        category.setUser(currentUser());
        category.setAmountOfArticles(categoryArticleRepository.countByCategoryCategoryId(category.getCategoryId()));
        categoryRepository.save(category);
    }

    @Override
    public ArticleDto createArticle(ArticleRequest articleRequest) {
        Set<Category> categories = articleRequest.getCategoriesId().stream()
                .map(this::getCategoryOrThrow)
                .collect(Collectors.toSet());

        if (articleRepository.findArticleByTitleAndUserUserId(articleRequest.getTitle(), currentUser().getUserId())
                .stream()
                .anyMatch(art -> art.getTitle().equalsIgnoreCase(articleRequest.getTitle().trim()))) {
            throw new CustomNotFoundException("The title : " + articleRequest.getTitle().trim() + " already exists.");
        }

        Article article = new Article(articleRequest, currentUser());
        articleRepository.save(article);

        Set<CategoryArticle> categoryArticles = categories.stream()
                .map(category -> new CategoryArticle(category, article))
                .collect(Collectors.toSet());
        categoryArticleRepository.saveAll(categoryArticles);

        categories.forEach(this::updateCategoryArticleCount);

        return article.toArticleResponseWithRelatedData();
    }

    @Override
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(Article::toArticleResponseWithRelatedData)
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " was found."));
    }

    @Transactional
    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.findArticleByArticleIdAndUserUserId(articleId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " was found."));
        articleRepository.deleteArticleByArticleIdAndUserUserId(articleId, currentUser().getUserId());
    }

    @Override
    public ArticleDto updateArticle(Long articleId, ArticleRequest articleRequest) {
        Article article = articleRepository.findArticleByArticleIdAndUserUserId(articleId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " was found."));

        if (articleRepository.findArticleByTitleAndUserUserId(articleRequest.getTitle(), currentUser().getUserId())
                .stream()
                .anyMatch(
                        art -> art.getTitle().equalsIgnoreCase(articleRequest.getTitle().trim())
                        && !art.getArticleId().equals(articleId)
                )) {
            throw new CustomNotFoundException("The title : " + articleRequest.getTitle().trim() + " already exists.");
        }

        articleRepository.save(articleRequest.toArticleEntity(article, currentUser()));

        Set<Category> categories = articleRequest.getCategoriesId().stream()
                .map(this::getCategoryOrThrow)
                .collect(Collectors.toSet());

        categoryArticleRepository.deleteByArticleArticleId(articleId);

        Set<CategoryArticle> categoryArticles = categories.stream()
                .map(category -> new CategoryArticle(category, article))
                .collect(Collectors.toSet());
        categoryArticleRepository.saveAll(categoryArticles);

        categories.forEach(this::updateCategoryArticleCount);

        return article.toArticleResponseWithRelatedData();
    }

    @Override
    public List<ArticleDto> getAllArticles(Integer pageNo, Integer pageSize, Enums.Article sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return articleRepository.findAll(pageable).getContent().stream()
                .map(Article::toArticleResponseWithRelatedData)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto createComment(Long articleId, CommentRequest commentRequest) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " was found."));

        commentService.createComment(commentRequest, article, currentUser());

        return article.toArticleResponseWithRelatedData();
    }

    @Override
    public List<ArticleDto> findAllArticlesByBookmark(Pageable pageable, Long userId) {
        return articleRepository.findAllArticlesByBookmark(pageable, userId).getContent()
                .stream()
                .map(Article::toArticleResponseWithRelatedData).toList();
    }

    @Override
    public ArticleDto getComments(Long articleId) {
        ArticleDto article = getArticle(articleId);
        if (article.getCommentList().isEmpty())
            throw new CustomNotFoundException("No comment found for article with Id : " + articleId + " was found.");

        return article;
    }
}
