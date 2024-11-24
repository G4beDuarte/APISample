package br.edu.atitus.apisample.controllers;

import br.edu.atitus.apisample.dtos.RegisterDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.RegisterService;
import br.edu.atitus.apisample.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    private final RegisterService service;
    private  final UserService userService;

    public RegisterController(RegisterService service, UserService userService) {
        super();
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public  ResponseEntity<RegisterEntity> createRegister(@RequestBody RegisterDTO registerDTO) throws Exception{
        // Converter DTO2Entity
        RegisterEntity newRegister = new RegisterEntity();
        BeanUtils.copyProperties(registerDTO, newRegister);
        // buscar usuario cadastrado
        UserEntity user = userService.findAll().get(0);
        newRegister.setUser(user);

        // invocar metodo sav
        service.save(newRegister);

        // retornar entidade salva
        return  ResponseEntity.status(HttpStatus.CREATED).body(newRegister);

    }

    @GetMapping
    public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception{
        var registers = service.findAll();

        //return ResponseEntity.status(200).body(registers);
        return ResponseEntity.ok(registers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterEntity> getOneRegister(@PathVariable UUID id) throws Exception{
        var register = service.findById(id);
        return ResponseEntity.ok(register);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RegisterEntity> putRegister(@PathVariable UUID id, @RequestBody RegisterDTO dto) throws Exception{
        // Converter DTO2Entity
        RegisterEntity register = service.findById(id);
        BeanUtils.copyProperties(dto, register);

        // invocar metodo save
        service.save(register);

        // retornar entidade salva
        return  ResponseEntity.ok(register);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegister(@PathVariable UUID id) throws Exception{
        service.deleteBYId(id);
        return ResponseEntity.ok("Registro deletado!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerException(Exception ex){
        String message = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(message);
    }
}
