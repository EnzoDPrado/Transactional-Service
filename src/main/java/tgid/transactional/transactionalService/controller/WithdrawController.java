package tgid.transactional.transactionalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tgid.transactional.transactionalService.client.Client;
import tgid.transactional.transactionalService.client.ClientRepository;
import tgid.transactional.transactionalService.company.Company;
import tgid.transactional.transactionalService.company.CompanyRepository;
import tgid.transactional.transactionalService.notification.Notification;
import tgid.transactional.transactionalService.withdraw.RegisterWithdrawData;
import tgid.transactional.transactionalService.withdraw.Withdraw;
import tgid.transactional.transactionalService.withdraw.WithdrawRepository;

@RestController
@RequestMapping("withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private Notification notification;

    @Operation(summary = "Do a withdraw", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdraw Register with success"),
            @ApiResponse(responseCode = "400", description = "Invalid Body"),
            @ApiResponse(responseCode = "500", description = "Invalid credentials(Duplicate entrys, invalid cpf, invalid company id, invalid client id)")
    })
    @PostMapping(value = "/{companyId}/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE )
    @Transactional
    public void register(@RequestBody @Valid RegisterWithdrawData data, @PathVariable Long companyId, @PathVariable Long clientId){
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Invalid company id: " + companyId));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Invalid client id: " + clientId));

        if(data.value() <= company.getBalance()){
            var tax = data.value() * 0.10;
            var withDrawValue = data.value() - tax;

            withdrawRepository.save(new Withdraw(data, clientId, companyId, tax));
            company.updateDeductCompanyBalance(data.value());
            client.updateClientBalance(withDrawValue);

            notification.sendEmailCallBack(company.getEmail(), "O cliente " + client.getNome() +" sacou um valor de " + data.value() +" dos fundos da sua empresa!");
            notification.sendEmailCallBack(client.getEmail(), "Um valor de " + withDrawValue +" foi sacado com sucesso da empresa " + company.getNome() +" atenção foi implementado um juros de 10% no valor de: " + data.value());

        }else{
            throw new IllegalArgumentException("Unavailable balance");
        }
    }
}
