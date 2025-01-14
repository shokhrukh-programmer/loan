package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.dto.Client;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Client>>> getClients() {
        APIResponseDTO<List<Client>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(
                clientService.getClients()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/clients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<ClientRegistrationResponseDTO>> addClient(
            @Valid @RequestBody ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        APIResponseDTO<ClientRegistrationResponseDTO> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage("Client has successfully registered!");
        apiResponseDTO.setData(
                clientService.registerClient(clientRegistrationRequestDTO)
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/clients/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> updateClient(
            @PathVariable("id") int id, @Valid @RequestBody ClientModificationRequestDTO clientModificationRequestDTO) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(
                clientService.updateClientById(id, clientModificationRequestDTO)
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}