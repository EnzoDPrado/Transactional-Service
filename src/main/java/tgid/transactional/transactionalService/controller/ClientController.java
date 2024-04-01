package tgid.transactional.transactionalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tgid.transactional.transactionalService.client.Client;
import tgid.transactional.transactionalService.client.ClientRepository;
import tgid.transactional.transactionalService.client.GetClientData;
import tgid.transactional.transactionalService.client.RegisterClientData;

import java.util.List;

@RestController
@RequestMapping(value = "client", produces = {"application/json"})
public class ClientController {

    @Autowired
    private ClientRepository repository;

    //Register client request and documentation
    @Operation(summary = "Do a client register", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client Register with success"),
            @ApiResponse(responseCode = "500", description = "Invalid credentials(Duplicate entrys, invalid cpf)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void register(@RequestBody @Valid RegisterClientData data){repository.save(new Client(data));}

    //Get Mapping request and documentation
    @Operation(summary = "Get all registered clients", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients get with success"),
    })
    @GetMapping()
    public List<GetClientData> list(){
        return repository.findAll().stream().map(GetClientData::new).toList();
    }


}
