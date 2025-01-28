package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.service.ClientService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO<List<Client>>> getClients() {
        APIResponseDTO<List<Client>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                clientService.getClients()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "{clientId:\\d+}")
    public ResponseEntity<APIResponseDTO<String>> updateClient(
            @PathVariable long clientId, @RequestBody @Valid ClientModificationRequestDTO client) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();

        clientService.updateClientById(clientId, client);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}