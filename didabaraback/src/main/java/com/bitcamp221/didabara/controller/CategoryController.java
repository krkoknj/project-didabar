package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryDTO;
import com.bitcamp221.didabara.dto.MyListDTO;
import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.service.CategoryItemService;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.LogMessage;
import com.bitcamp221.didabara.util.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UploadFile uploadFile;

  @Autowired
  private CategoryItemService categoryItemService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : host의 category 리스트 맵핑
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myList")
  public ResponseEntity<?> findMyList(@AuthenticationPrincipal final String userId) {
    final String message = "category myList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        final List<CategoryEntity> categoryEntities = categoryService.findMyList(Long.valueOf(userId));

        List<MyListDTO> list = new ArrayList<>();

        for (CategoryEntity c : categoryEntities) {
          MyListDTO myListDTO = new MyListDTO(c);

          list.add(myListDTO);

          list.get(list.size() - 1).setCount(categoryItemService.countByCategory(c.getId()));
        }

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(list);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorJoin(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : category 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @GetMapping("/page/{categoryId}")
  public ResponseEntity<?> findCategory(@PathVariable(value = "categoryId") final Long categoryId) {
    final String message = "category findCategory";

    try {
      log.info(LogMessage.infoJoin(message));

      if (categoryId != null) {
        final CategoryDTO categoryDTO = new CategoryDTO(categoryService.findCategory(categoryId));

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(categoryDTO);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorJoin(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 카테고리 생성 (생성 후 카테고리 내부)
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(title, content), ImageFile
//  -----------------------------------------------------
  @PostMapping("/create")
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @RequestPart(value = "categoryDTO", required = false) final CategoryDTO categoryDTO,
                                  @RequestPart(value = "file", required = false) final MultipartFile file) {
    final String message = "category create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryDTO != null) {

        final String code = UUID.randomUUID().toString().substring(0, 8);

        if (file != null) {
          final String filePath = uploadFile.uploadCategoryImage(file);

          categoryDTO.setProfileImageUrl(filePath);
        } else {

          categoryDTO.setProfileImageUrl(categoryDTO.getProfileImageUrl());
        }

        categoryDTO.setInviteCode(code);
        categoryDTO.setHost(Long.valueOf(userId));

        final Optional<CategoryEntity> entity = categoryService.create(CategoryDTO.toEntity(categoryDTO));

        final CategoryDTO DTO = new CategoryDTO(entity);

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(DTO);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorJoin(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 내 카테고리 수정 (수정 후 카테고리 내부)
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id[필수], title, content), imageFile
//  -----------------------------------------------------
  @PutMapping("/update")
  public ResponseEntity<?> update(@AuthenticationPrincipal final String userId,
                                  @RequestPart(value = "categoryDTO", required = false) final CategoryDTO categoryDTO,
                                  @RequestPart(value = "file", required = false) final MultipartFile file) {
    final String message = "category update";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && Long.valueOf(userId) == categoryService.findHost(categoryDTO.getId())) {
        categoryDTO.setHost(Long.valueOf(userId));

        if (file != null) {
          final String filePath = uploadFile.uploadCategoryImage(file);

          categoryDTO.setProfileImageUrl(filePath);
        } else {

          categoryDTO.setProfileImageUrl(categoryService.findUrl(categoryDTO.getId()));
        }

        final Optional<CategoryEntity> entity = categoryService
                .update(CategoryDTO.toEntity(categoryDTO));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCategoryDTO(entity);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 Category 삭제 (삭제 후 내 리스트 출력)
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete/page/{categoryId}")
  public ResponseEntity<?> delete(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryId") final Long categoryId) {
    final String message = "category delete";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && Long.valueOf(userId) == categoryService.findHost(categoryId)) {
        final List<CategoryEntity> categoryEntities = categoryService.deleteById(Long.valueOf(userId), categoryId);

//        uploadFile.deleteFile(categoryService.findUrl(categoryId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCategoryDTO(categoryEntities);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}