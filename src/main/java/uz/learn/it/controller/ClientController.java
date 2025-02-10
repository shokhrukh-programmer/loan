package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<APIResponseDTO<List<Client>>> getClients() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<Client>>builder()
                        .data(clientService.getClients())
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PutMapping(value = "/{clientId:\\d+}")
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