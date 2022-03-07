package com.example.demo;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class SimpleController {

    @Autowired
    private ResourceLoader resourceLoader;

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
        extracted(response, name);
    }

    @GetMapping("template2")
    public void getSimpleTemplate2(HttpServletResponse response) throws Exception {

        String name = "classpath:template2.html";
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", "correctly");
        extracted(response, name, vars);
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
        extracted(response, name);
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
        extracted(response, name, vars);
    }

    private void extracted(HttpServletResponse response, String name) throws IOException, DocumentException {
        extracted(response, name, null);
    }

    private void extracted(HttpServletResponse response, String name, Map<String, Object> vars) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        Resource resource = resourceLoader.getResource(name);
        String template2 = new String(Files.readAllBytes(resource.getFile().toPath()));
        String[] holder = new String[]{template2};
        if (vars != null) {
            vars.forEach((String k, Object v) -> {
                holder[0] = holder[0].replace("${" + k.trim() + "}", v.toString());
            });
        }
        renderer.setDocumentFromString(holder[0]);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }
}

