package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.entity.Client;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Client>>> getClients() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<Client>>builder()
                        .data(clientService.getClients())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<ClientRegistrationResponseDTO>> registerClient(
            @Valid @RequestBody ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        return new ResponseEntity<>(
                APIResponseDTO.<ClientRegistrationResponseDTO>builder()
                        .message(SuccessfulMessageConstants.CLIENT_REGISTERED_SUCCESSFULLY_MESSAGE)
                        .data(clientService.registerClient(clientRegistrationRequestDTO))
                        .build(), HttpStatus.OK
        );
    }

    @PutMapping(value = "/{clientId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> updateClient(
            @PathVariable("clientId") long clientId,
            @RequestBody ClientModificationRequestDTO clientModificationRequestDTO) {
        clientService.updateClientById(clientId, clientModificationRequestDTO);

        return new ResponseEntity<>(
                APIResponseDTO.<String>builder()
                        .build(), HttpStatus.OK
        );
    }
}