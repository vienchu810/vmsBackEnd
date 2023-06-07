package com.example.vms.controller;

import com.example.vms.data.model.VMSong;

import com.example.vms.data.repository.VmSingRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class SongController {
    private final VmSingRepository vmSingRepository;

    @GetMapping("/getSong")
    public ResponseEntity findAll() {
        return Response.data(vmSingRepository.findAll(Sort.by("id")));
    }


    @PostMapping("/addSong")
    public ResponseEntity addCate(@RequestBody List<VMSong> vmSongList) {
        for (VMSong vmSong : vmSongList) {
            Long id = vmSong.getIdSong();
            String nameSong = vmSong.getNameSong();
            String song = vmSong.getSong();
            String singer = vmSong.getSinger();
            Long cate = vmSong.getCate();
            String comment = vmSong.getCommentUser();


            VMSong vmSongNew = new VMSong();
            vmSongNew.setIdSong(id);
            vmSongNew.setNameSong(nameSong);
            vmSongNew.setSong(song);
            vmSongNew.setSinger(singer);
            vmSongNew.setCate(cate);
            vmSongNew.setCommentUser(comment);


            vmSongNew = vmSingRepository.save(vmSongNew);

        }
        return ResponseEntity.ok().build();
    }
}
