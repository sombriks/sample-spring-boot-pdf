package com.example.demo;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class SimpleController {

    @Autowired
    private PdfService service;

    @GetMapping
    public Pojo hello() {
        return new Pojo("hi");
    }

    /**
     * simply pass unmodified template to output
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("template1")
    public void getSimpleTemplate1(HttpServletResponse response) throws Exception {
        String name = "classpath:template1.html";
        render(response, name, null);
    }

    @GetMapping("template2")
    public void getSimpleTemplate2(HttpServletResponse response) throws Exception {

        String name = "classpath:template2.html";
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", "correctly");
        render(response, name, vars);
    }


    /**
     * this one give us error because the document is invalid
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("template3")
    public void getSimpleTemplate3(HttpServletResponse response) throws Exception {
        String name = "classpath:template3.html";
        render(response, name, null);
    }

    /**
     * a more complex, with variables and some css
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("template4")
    public void getSimpleTemplate4(HttpServletResponse response) throws Exception {
        String name = "classpath:template4.html";
        Map<String, Object> vars = new HashMap<>();
        vars.put("customer", "Mr. Robot");
        render(response, name, vars);
    }

    private void render(HttpServletResponse response, String name, Map<String, Object> vars) throws IOException, DocumentException {
        service.render(response.getOutputStream(), name, vars);
    }


}

