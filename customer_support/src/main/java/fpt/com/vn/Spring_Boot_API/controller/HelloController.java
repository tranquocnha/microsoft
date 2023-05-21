/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/microservices")
    public String getData() {
        return "MicroServices";
    }

    @GetMapping("/welcom/{name}")
    public String Welcom(@PathVariable String name) {
        return "Welcom " + name;
    }
}
