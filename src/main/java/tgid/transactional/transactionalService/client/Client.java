package tgid.transactional.transactionalService.client;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity(name = "Client")
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private Double balance;
    private boolean isCpfValid(String cpfDigits) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpfDigits.charAt(i)) * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier == 10 || firstVerifier == 11) {
            firstVerifier = 0;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpfDigits.charAt(i)) * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier == 10 || secondVerifier == 11) {
            secondVerifier = 0;
        }

        return cpfDigits.charAt(9) == Character.forDigit(firstVerifier, 10) &&
                cpfDigits.charAt(10) == Character.forDigit(secondVerifier, 10);
    }

    public Client(RegisterClientData data) {
        if(isCpfValid(data.cpf())){
            this.nome = data.nome();
            this.email = data.email();
            this.cpf = data.cpf();
            this.balance = 0.0;
        }else{
            throw new IllegalArgumentException("Invalid cpf: " + data.cpf());
        }
    }

    public void updateClientBalance(double value) {
        this.balance += value;
    }
}
