package com.fpt.g52.microsoft.controller;


import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fpt.g52.microsoft.model.DTO.HelloWorkDTO;
import com.fpt.g52.microsoft.model.HelloWorkEntity;
import com.fpt.g52.microsoft.security.HelloWorkRepository;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/microsoft/v1")
public class HelloWorkController {
    @Autowired
    private HelloWorkRepository helloWorkRepository;

    @GetMapping("/hello-work")
    public List<HelloWorkEntity> getAllHelloWork() {
        return helloWorkRepository.findAll();
    }

    @GetMapping("/hello-work/{id}")
    public ResponseEntity<HelloWorkEntity> getHelloWorkById(@PathVariable(value = "id") int id)
            throws ResourceNotFoundException {
        HelloWorkEntity helloWorkDTO = helloWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HelloWork not found for this id :: " + id));
        return ResponseEntity.ok().body(helloWorkDTO);
    }

    @PostMapping("/hello-work")
    public ResponseEntity<HelloWorkEntity> createEmployee(@RequestBody  HelloWorkEntity helloWorkEntity) {
        try{
            HelloWorkEntity helloWork = helloWorkRepository.save(new HelloWorkEntity(helloWorkEntity.getContext()));
            return new ResponseEntity<>(helloWork, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/hello-work/{id}")
    public ResponseEntity<HelloWorkEntity> updateEmployee(@PathVariable(value = "id") int id,
                                                          @RequestBody  HelloWorkEntity helloWorkEntity) throws ResourceNotFoundException {
            HelloWorkEntity helloWork = helloWorkRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("HelloWork not found for this id :: " + id));

        try{
            helloWork.setContext(helloWorkEntity.getContext());
            return new ResponseEntity<>(helloWorkRepository.save(helloWork),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/hello-work/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") int id)
            throws ResourceNotFoundException {
        HelloWorkEntity employee = helloWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HelloWork not found for this id :: " + id));

        helloWorkRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
