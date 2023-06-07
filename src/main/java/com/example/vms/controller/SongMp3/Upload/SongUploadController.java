package com.example.vms.controller.SongMp3.Upload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class SongUploadController {
    @PostMapping("/uploadSong")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
        // Kiểm tra xem tệp có tồn tại không
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng chọn một tệp MP3 để tải lên.");
        }

        // Kiểm tra loại tệp có phải là MP3 hay không
        if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".mp3")) {
            return ResponseEntity.badRequest().body("Vui lòng chỉ chọn tệp MP3.");
        }

        // Xử lý tệp MP3 tại đây
        // Ví dụ: Lưu trữ tệp trên máy chủ
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            // Cấu hình đường dẫn lưu trữ tệp MP3
            String uploadDir = "/path/to/upload/directory/";
            SongUploadUtil.saveFile(uploadDir, fileName, file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xảy ra khi lưu trữ tệp MP3.");
        }

        return ResponseEntity.ok("Tệp MP3 đã được tải lên thành công");
    }
}
