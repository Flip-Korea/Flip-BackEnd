package com.flip.flipapp.domain.scrap.service.dto;

public record AddScrapCommand(

    Long profileId,
    Long postId,
    String scrapComment
) {

}
