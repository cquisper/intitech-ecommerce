package com.cquisper.msvc.blog.controllers;

import com.cquisper.msvc.blog.models.BlogCategory;
import com.cquisper.msvc.blog.services.BlogCategoryService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class BlogCategoryController {

    private final BlogCategoryService blogCategoryService;

    @GetMapping("/all")
    @ResponseStatus(OK)
    public Flux<BlogCategory> getAllBlogCategories(){
        return this.blogCategoryService.getAllBlogCategories();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(OK)
    public Mono<BlogCategory> getBlogCategoryById(@PathVariable String id){
        return this.blogCategoryService.getBlogCategoryById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public Mono<BlogCategory> createBlogCategory(@RequestBody BlogCategory blogCategory){
        return this.blogCategoryService.createBlogCategory(blogCategory);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(OK)
    public Mono<BlogCategory> updateBlogCategory(@RequestBody BlogCategory blogCategory, @PathVariable String id){
        return this.blogCategoryService.updateBlogCategory(blogCategory, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteBlogCategoryById(@PathVariable String id){
        return this.blogCategoryService.deleteCategoryById(id);
    }
}
