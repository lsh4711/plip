package com.server.global.doc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocController {
    // 환경 변수로 할까 고민했으나 여러분의 원성이 두려웠습니다..
    // @Value("${redirect-url}")
    private String url = "https://teamdev.shop:8000";

    @GetMapping
    public void getDocs(HttpServletResponse response) throws IOException {
        response.sendRedirect(url + "/docs/swagger-ui/index.html");
    }
}
