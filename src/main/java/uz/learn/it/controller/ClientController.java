package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<uz.learn.it.entity.Client>>> getClients() {
        APIResponseDTO<List<uz.learn.it.entity.Client>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                clientService.getClients()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<ClientRegistrationResponseDTO>> registerClient(
            @Valid @RequestBody ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        APIResponseDTO<ClientRegistrationResponseDTO> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(Constants.CLIENT_REGISTERED_SUCCESSFULLY_MESSAGE);

        apiResponseDTO.setData(
                clientService.registerClient(clientRegistrationRequestDTO)
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> updateClient(
            @RequestBody Client client) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();

        clientService.updateClientById(client);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}