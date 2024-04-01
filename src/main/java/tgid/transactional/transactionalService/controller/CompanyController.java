package tgid.transactional.transactionalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tgid.transactional.transactionalService.company.Company;
import tgid.transactional.transactionalService.company.CompanyRepository;
import tgid.transactional.transactionalService.company.GetCompanyData;
import tgid.transactional.transactionalService.company.RegisterCompanyData;

import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

    //Register company request and documentation
    @Operation(summary = "Do a company register", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company Register with success"),
            @ApiResponse(responseCode = "500", description = "Invalid credentials(Duplicate entrys, invalid cnpj)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void register(@RequestBody @Valid RegisterCompanyData data){
        repository.save(new Company(data));
    }

    //Get company's request and documentation
    @Operation(summary = "Get all registered companys", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients get with success"),
    })
    @GetMapping
    public List<GetCompanyData> list(){
        return repository.findAll().stream().map(GetCompanyData::new).toList();
    }

}
