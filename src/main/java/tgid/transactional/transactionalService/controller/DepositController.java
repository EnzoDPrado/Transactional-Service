package tgid.transactional.transactionalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tgid.transactional.transactionalService.client.Client;
import tgid.transactional.transactionalService.client.ClientRepository;
import tgid.transactional.transactionalService.company.Company;
import tgid.transactional.transactionalService.company.CompanyRepository;
import tgid.transactional.transactionalService.deposit.Deposit;
import tgid.transactional.transactionalService.deposit.DepositRepository;
import tgid.transactional.transactionalService.deposit.RegisterDepositData;
import tgid.transactional.transactionalService.notification.Notification;

@RestController
@RequestMapping("deposit")
public class DepositController {
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private Notification notification;

    //Register deposit request and documentation
    @Operation(summary = "Do a deposit register", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit Register with success"),
            @ApiResponse(responseCode = "400", description = "Invalid Body"),
            @ApiResponse(responseCode = "500", description = "Invalid credentials(Duplicate entrys, invalid cpf, invalid company id, invalid client id)")
    })
    @PostMapping(value = "/{clientId}/{companyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void register(@RequestBody @Valid RegisterDepositData data, @PathVariable Long clientId, @PathVariable Long companyId){
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Invalid company id: " + companyId));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Invalid client id: " + clientId));

        var tax = data.value() * 0.10;
        var depositValue = data.value() - tax;

        company.updateIncreaseCompanyBalance(depositValue);
        depositRepository.save(new Deposit(data, clientId, companyId, tax));

        notification.sendEmailCallBack(company.getEmail(), "Um valor de " + depositValue +" foi adicionado aos fundos da sua empresa!");
        notification.sendEmailCallBack(client.getEmail(), "Um valor de " + depositValue +" foi depositado na empresa " + company.getNome() + " em seu nome!, atenção foi implementado um juros de 10% no valor de: " + data.value());

    }

}
