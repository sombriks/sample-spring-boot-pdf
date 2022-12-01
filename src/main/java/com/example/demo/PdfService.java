package com.example.demo;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private ResourceLoader resourceLoader;

    public void render(OutputStream stream, String name, Map<String, Object> vars) throws IOException, DocumentException {
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
        renderer.createPDF(stream);
    }
}
