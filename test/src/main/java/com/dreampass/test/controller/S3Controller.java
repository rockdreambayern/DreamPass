package com.dreampass.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/s3")
@Slf4j
public class S3Controller {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path META_FILE;

    static {
        try {
            // 从 classpath 获取资源 URL
            URL resourceUrl = S3Controller.class.getClassLoader().getResource("object-metadata.json");
            if (resourceUrl == null) {
                throw new FileNotFoundException("object-metadata.json not found in resources!");
            }
            META_FILE = Paths.get(resourceUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load metadata file", e);
        }
    }

    private final Map<String, String> metadata = new ConcurrentHashMap<>();

    public S3Controller() throws IOException {
        // 初始化时加载 JSON 文件
        if (Files.exists(META_FILE)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> loaded = mapper.readValue(META_FILE.toFile(), new TypeReference<>() {});
            metadata.putAll(loaded);
        }
    }

    @PostMapping("/{bucket}/{objectKey}")
    public ResponseEntity<String> upload(@PathVariable String bucket,
                                         @PathVariable String objectKey,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传文件处理:{}", objectKey);
        Path bucketDir = DATA_DIR.resolve(bucket);
        Files.createDirectories(bucketDir);

        // 生成唯一文件名，避免覆盖
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = bucketDir.resolve(uniqueFileName);
        file.transferTo(filePath);

        // 保存 objectKey -> originalFilename 映射关系
        metadata.put(bucket + "/" + objectKey, file.getOriginalFilename());

        persistMetadata();

        log.info("上传文件成功:{}", objectKey);

        return ResponseEntity.ok("Upload success: " + objectKey);
    }

    @GetMapping("/{bucket}/{objectKey}")
    public ResponseEntity<Resource> download(@PathVariable String bucket,
                                             @PathVariable String objectKey) throws IOException {
        String key = bucket + "/" + objectKey;
        String originalFilename = metadata.get(key);

        if (originalFilename == null) {
            return ResponseEntity.notFound().build();
        }

        Path bucketDir = DATA_DIR.resolve(bucket);
        try (Stream<Path> files = Files.list(bucketDir)) {
            Path filePath = files
                    .filter(path -> path.getFileName().toString().endsWith(originalFilename))
                    .findFirst()
                    .orElse(null);

            if (filePath == null) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(filePath);

            // 根据扩展名判断 MIME 类型
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        }
    }

    private synchronized void persistMetadata() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(META_FILE.toFile(), metadata);
    }
}
