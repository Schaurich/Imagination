package br.pucrs.imgnation.controller;

import br.pucrs.imgnation.service.FileService;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PrincipalController {
    @Value("${welcome.message}")
    private String message;

    @Autowired
    FileService fileService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        return "principal"; //view
    }

    @GetMapping("/album")
    public String album(Model model) {
        return "album"; //view
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
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

    @GetMapping("/l")
    public String teste(Model model) {
        Map<String, Object> mapModel = new HashMap<>();
        mapModel.put("paginaRetorno", "album");

        ObjectListing objectListing = fileService.listObjects();
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println(os.getKey());
            mapModel.put("files", "https://dbimgnation.s3-sa-east-1.amazonaws.com/" + os.getKey());
        }

        model.addAllAttributes(mapModel);
        return (String)mapModel.get("paginaRetorno");
    }



}