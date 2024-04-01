package tgid.transactional.transactionalService.company;

public record GetCompanyData(
        Long id,
        Double balance,
        String nome,
        String email,
        String cnpj

) {
    public GetCompanyData(Company company){
        this(company.getId(), company.getBalance(), company.getNome(), company.getEmail(), company.getCnpj());
    }
}
