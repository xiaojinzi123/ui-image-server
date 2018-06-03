package com.xiaojinzi.image;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter printWriter = new PrintWriter(response.getOutputStream());

        printWriter.print("hello");

        printWriter.flush();

    }

}
