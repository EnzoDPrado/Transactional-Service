package tgid.transactional.transactionalService.client;
public record GetClientData(
        Long id,
        Double balance,
        String nome,
        String email,
        String cpf

) {
    public GetClientData(Client client){
        this(client.getId(), client.getBalance(), client.getNome(), client.getEmail(), client.getCpf());
    }
}
