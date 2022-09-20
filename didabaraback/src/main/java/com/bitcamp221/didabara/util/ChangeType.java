package com.bitcamp221.didabara.util;

import com.bitcamp221.didabara.dto.*;
import com.bitcamp221.didabara.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ChangeType {

  public static ResponseEntity toUserDTO(final List<UserEntity> entities) {
    final List<UserDTO> userDTOS = entities
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<UserDTO> responseDTO = ResponseDTO
            .<UserDTO>builder()
            .resList(userDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toCategoryDTO(final List<CategoryEntity> entities) {
    final List<CategoryDTO> categoryDTOS = entities
            .stream()
            .map(CategoryDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<CategoryDTO> responseDTO = ResponseDTO
            .<CategoryDTO>builder()
            .resList(categoryDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toCategoryDTO(final Optional<CategoryEntity> entities) {
    final List<CategoryDTO> categoryDTOS = entities
            .stream()
            .map(CategoryDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<CategoryDTO> responseDTO = ResponseDTO
            .<CategoryDTO>builder()
            .resList(categoryDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toCategoryItemDTO(final List<CategoryItemEntity> entities) {
    final List<CategoryItemDTO> categoryItemDTOS = entities
            .stream()
            .map(CategoryItemDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<CategoryItemDTO> responseDTO = ResponseDTO
            .<CategoryItemDTO>builder()
            .resList(categoryItemDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toItemReplyDTO(final List<CategoryItemReplyEntity> entities) {
    final List<CategoryItemReplyDTO> replyDTOS = entities
            .stream()
            .map(CategoryItemReplyDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<CategoryItemReplyDTO> responseDTO = ResponseDTO
            .<CategoryItemReplyDTO>builder()
            .resList(replyDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toReportDTO(final List<ReportEntity> entities) {
    final List<ReportDTO> reportDTOS = entities
            .stream()
            .map(ReportDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<ReportDTO> responseDTO = ResponseDTO
            .<ReportDTO>builder()
            .resList(reportDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toSubscriberDTO(final List<SubscriberEntity> entities) {
    final List<SubscriberDTO> subscriberDTOS = entities
            .stream()
            .map(SubscriberDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<SubscriberDTO> responseDTO = ResponseDTO
            .<SubscriberDTO>builder()
            .resList(subscriberDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toCheckedDTO(final List<UserEntity> entities) {
    final List<UserDTO> userDTOS = entities
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());

    final ResponseDTO<UserDTO> responseDTO = ResponseDTO
            .<UserDTO>builder()
            .resList(userDTOS)
            .build();

    return ResponseEntity.ok().body(responseDTO);
  }

  public static ResponseEntity toException(final Exception e) {
    final ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

    return ResponseEntity.badRequest().body(responseDTO);
  }
}
