package com.flip.flipapp.domain.block.controller;

import com.flip.flipapp.domain.block.controller.dto.request.BlockRequest;
import com.flip.flipapp.domain.block.model.Block;
import com.flip.flipapp.domain.block.service.BlockService;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class BlockController {

  private final BlockService blockService;

  @PostMapping("api/v1/block")
  public ResponseEntity<Object> block(
      @RequestBody @Valid BlockRequest blockRequest,
      @CurrentProfile Long blockerProfileId
  ) {
    Block block = blockService.block(blockRequest.toCommand(blockerProfileId));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{blockId}")
        .buildAndExpand(block.getBlockId())
        .toUri();

    return ResponseEntity.created(location).build();
  }
}
