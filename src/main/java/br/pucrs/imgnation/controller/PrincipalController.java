package br.pucrs.imgnation.controller;

import br.pucrs.imgnation.service.FileService;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrincipalController {
    @Value("${welcome.message}")
    private String message;

    @Autowired
    FileService fileService;

    private static final String URL_AWS_S3 = "https://dbimgnation.s3-sa-east-1.amazonaws.com/";

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        return "principal"; //view
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        Map<String, Object> mapModel = new HashMap<>();
        if (!file.isEmpty()) {
            fileService.uploadObject(file);
            mapModel.put("msgSucess", "Arquivo enviado com sucesso!");
        } else {
            mapModel.put("msgAlert", "Favor, escolha um arquivo para upload!");
        }
        mapModel.put("paginaRetorno", "principal");
        model.addAllAttributes(mapModel);
        return (String)mapModel.get("paginaRetorno");
    }

    @GetMapping("/album")
    public String teste(Model model) {
        Map<String, Object> mapModel = new HashMap<>();
        List<String> imgList = new ArrayList<>();
        mapModel.put("paginaRetorno", "album");

        ObjectListing objectListing = fileService.listObjects();
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            imgList.add(URL_AWS_S3 + os.getKey());
        }
        mapModel.put("files", imgList);
        model.addAllAttributes(mapModel);
        return (String)mapModel.get("paginaRetorno");
    }

}