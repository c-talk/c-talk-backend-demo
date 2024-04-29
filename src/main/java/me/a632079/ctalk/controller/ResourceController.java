package me.a632079.ctalk.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.stream.StreamUtil;
import io.github.thibaultmeyer.cuid.CUID;
import me.a632079.ctalk.constant.CTalkConstant;
import me.a632079.ctalk.dto.ResourceDto;
import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.enums.ResourceType;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.ResourcePo;
import me.a632079.ctalk.response.SkipPackage;
import me.a632079.ctalk.service.ResourceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/resources")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @SkipPackage
    @GetMapping("/{id}")
    public void getResource(@PathVariable String id, HttpServletResponse response) throws IOException {
        var resource = resourceService.getResource(id);
        if (resource == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        response.setContentType(MediaType.valueOf(resource.getMime())
                                         .getType());
        response.getOutputStream()
                .write(resource.getBytes());
        response.flushBuffer();
    }

    @SkipPackage
    @ResponseBody
    @DeleteMapping("/{id}")
    // TODO: 添加 CAS 鉴权？仅限管理员删除？
    public ResponseEntity<Void> deleteResource(@PathVariable String id) {
        var result = resourceService.removeResource(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping("/")
    public List<ResourcePo> createResource(@RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.FILE_EMPTY);
        }
        return Arrays.stream(files)
                     .map(v -> {
                                 ResourceDto dto = new ResourceDto();
                                 dto.setName(v.getOriginalFilename());
                                 dto.setId(CUID.randomCUID2()
                                               .toString());
                                 dto.setType(ResourceType.Image); // Now just pass image
                                 dto.setMime(v.getContentType());
                                 dto.setData(v);
                                 return resourceService.addResource(dto);
                             }
                     )
                     .collect(Collectors.toList());
    }
}
