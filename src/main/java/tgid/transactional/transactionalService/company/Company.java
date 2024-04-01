package tgid.transactional.transactionalService.company;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity(name = "Company")
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cnpj;
    private Double balance;

    public boolean isCnpjValid(String cnpj) {
        int sum = 0;
        int weight = 5;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight;
            weight--;
            if (weight == 1) {
                weight = 9;
            }
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier >= 10) {
            firstVerifier = 0;
        }

        sum = 0;
        weight = 6;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight;
            weight--;
            if (weight == 1) {
                weight = 9;
            }
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier >= 10) {
            secondVerifier = 0;
        }

        return Character.getNumericValue(cnpj.charAt(12)) == firstVerifier &&
                Character.getNumericValue(cnpj.charAt(13)) == secondVerifier;
    }

    public Company(RegisterCompanyData data) {
        if(isCnpjValid(data.cnpj())){
            this.nome = data.nome();
            this.email = data.email();
            this.cnpj = data.cnpj();
            this.balance = 0.0;
        }else{
            throw new IllegalArgumentException("Invalid cnpj: " + data.cnpj());
        }

    }

    public void updateIncreaseCompanyBalance(double value) {
        this.balance += value;
    }

    public void updateDeductCompanyBalance(double value) {
        this.balance -= value;
    }
}
