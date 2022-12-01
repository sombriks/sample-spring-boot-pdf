package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootTest
public class RenderPdfTests {

    @Autowired
    private PdfService service;

    @Test
    public void shouldRenderTemplate1() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        service.render(stream, "classpath:template1.html", null);
        OutputStream out = new FileOutputStream("target/template1.pdf");
        out.write(stream.toByteArray());
        out.close();
    }
}
