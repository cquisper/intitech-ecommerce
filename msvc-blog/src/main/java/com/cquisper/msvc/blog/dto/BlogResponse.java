package com.cquisper.msvc.blog.dto;

import com.cquisper.msvc.blog.models.Comment;
import com.cquisper.msvc.blog.models.Image;
import com.cquisper.msvc.blog.models.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record BlogResponse(
        String id,
        String title,
        String category,
        User author,
        String description,
        Integer numViews,
        Integer numLikes,
        Set<User> likeUsers,
        Integer numComments,
        List<Comment> comments,
        List<Image> images,
        LocalDateTime createdAt
) {
}