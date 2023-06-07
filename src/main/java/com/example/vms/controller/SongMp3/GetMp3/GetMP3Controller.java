package com.example.vms.controller.SongMp3.GetMp3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class GetMP3Controller {

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Đường dẫn tới thư mục chứa các tệp MP3
        String uploadDirectory = "/path/to/upload/directory";

        // Xây dựng đường dẫn đầy đủ tới tệp MP3
        Path filePath = Paths.get(uploadDirectory).resolve(fileName);

        try {
            // Đọc nội dung tệp MP3 vào một mảng byte
            byte[] fileData = Files.readAllBytes(filePath);

            // Tạo một đối tượng Resource từ mảng byte
            Resource resource = new ByteArrayResource(fileData);

            // Xác định kiểu MIME cho tệp MP3
            String contentType = Files.probeContentType(filePath);

            // Tạo header để chỉ định kiểu MIME và tên tệp cho response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", fileName);

            // Trả về response chứa tệp MP3
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
