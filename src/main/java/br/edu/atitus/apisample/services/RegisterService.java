package br.edu.atitus.apisample.services;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.repositories.RegisterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegisterService {

    private final RegisterRepository repository;

    public RegisterService(RegisterRepository repository) {
        super();
        this.repository = repository;
    }
    // CRUD
    // C = Create
    // R = Read
    // U = Update
    // D = Delete

    // método save
    public RegisterEntity save(RegisterEntity newRegister) throws  Exception {
        // validações de regra de negócio
        if (newRegister.getUser() == null || newRegister.getUser().getId() == null)
            throw new Exception("Usuário não informado");
        if (newRegister.getLatitude() < -90 || newRegister.getLatitude() > 90)
            throw new Exception("Latitude inválida");
        if (newRegister.getLongitude() < -180 || newRegister.getLongitude() > 180)
            throw new Exception("Longitude inválida");
        // Invocar método save da camada repository
        repository.save(newRegister);

        // Retornar o objeto salvo
        return newRegister;
    }

    // método findAll
    public List<RegisterEntity> findAll() throws Exception{
        List<RegisterEntity> registers = repository.findAll();
        return registers;
    }
    // método findById
    public RegisterEntity findById(UUID id) throws Exception{
        RegisterEntity register = repository.findById(id).orElseThrow(() ->new Exception("Registro não encontrado com este ID"));
        return register;
    }

    // método deleteById
    public void deleteBYId(UUID id) throws Exception{
        this.findById(id);
        repository.deleteById(id);
    }

}