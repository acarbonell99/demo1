package com.example.demo.controller;

import com.example.demo.domain.model.File;
import com.example.demo.domain.model.Movie;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileRepository fileRepository;
    FileController(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @GetMapping("/id")
    public byte[] jajaja(@PathVariable UUID id){
        File file =  fileRepository.getById(id);
        return file.data;
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile uploadedFile) {
        try {
            File file = new File();
            file.contenttype = uploadedFile.getContentType();
            file.data = uploadedFile.getBytes();

            return fileRepository.save(file).fileid.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping
    public String hack() {
        List<String> files = fileRepository.getFileIds();

        String filesStr = "";
        for (String file : files) {
            filesStr += "<img src='/files/"+file+"' style='width:15em'>";
        }

        return "<form method=\"POST\" enctype=\"multipart/form-data\" style=\"display:flex;\">\n" +
                "    <input id=\"file\" type=\"file\" name=\"file\" style=\"display:none\" onchange=\"document.getElementById('preview').src=window.URL.createObjectURL(event.target.files[0])\">\n" +
                "    <label for=\"file\" style=\"border: 1px dashed #999\">\n" +
                "        <img id=\"preview\" src=\"\" style=\"width:64px;\">\n" +
                "    </label>\n" +
                "    <input type=\"submit\" style=\"background:#0096f7;color: white;border: 0;border-radius: 3px;padding: 8px;\" value=\"Upload\">\n" +
                "</form>\n <div style='display:flex;flex-wrap:wrap;gap:1em;'>" + filesStr + "</div>";
    }
}
