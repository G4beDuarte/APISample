package br.edu.atitus.apisample.controllers;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.services.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    private final RegisterService service;

    public RegisterController(RegisterService service) {
        super();
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception{
        var registers = service.findAll();

        //return ResponseEntity.status(200).body(registers);
        return ResponseEntity.ok(registers);
    }
}