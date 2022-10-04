package pl.setlikd.todolist.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InfoController {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${my.prop}")
    private  String myProp;

    @GetMapping("/info/url")
    String url () {
        return this.url;
    }

    @GetMapping("info/prop")
    String myPrpo() {
        return this.myProp;
    }
}
