package com.cquisper.msvc.blog.services;

import com.cquisper.msvc.blog.models.BlogCategory;
import com.cquisper.msvc.blog.repository.BlogCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service @Slf4j
@RequiredArgsConstructor
public class BlogCategoryService {

    private final BlogCategoryRepository blogCategoryRepository;

    public Mono<BlogCategory> getBlogCategoryById(String id) {
        return this.blogCategoryRepository.findById(id);
    }

    public Flux<BlogCategory> getAllBlogCategories() {
        return this.blogCategoryRepository.findAll();
    }

    public Mono<BlogCategory> createBlogCategory(BlogCategory blogCategory) {
        return this.blogCategoryRepository.existsByNameIgnoreCase(blogCategory.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new Exception("Category already exists"));
                    }
                    return this.blogCategoryRepository.save(blogCategory);
                })
                .doOnSuccess(blogCat -> log.info("Created category: {}", blogCat.getName()));
    }

    public Mono<BlogCategory> updateBlogCategory(BlogCategory blogCategory, String id) {
        return this.blogCategoryRepository.findById(id)
                .flatMap(blogCategoryC -> {
                    if (blogCategory.getName().equals(blogCategoryC.getName())) {
                        return Mono.empty();
                    }
                    return this.blogCategoryRepository.existsByNameIgnoreCase(blogCategory.getName())
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.error(new Exception("Category already exists"));
                                }
                                blogCategoryC.setName(blogCategory.getName());
                                return this.blogCategoryRepository.save(blogCategoryC);
                            });
                })
                .doOnSuccess(blogCat -> log.info("Updated category: {}", blogCat.getName()));
    }

    public Mono<Void> deleteCategoryById(String id){
        return this.blogCategoryRepository.deleteById(id)
                .doOnTerminate(() -> log.info("Delete blog category with id {}", id));
    }
}
