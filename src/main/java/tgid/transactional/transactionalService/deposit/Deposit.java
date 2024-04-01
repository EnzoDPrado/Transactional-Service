package tgid.transactional.transactionalService.deposit;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity(name = "Deposit")
@Table(name = "deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    private Double tax;
    private Long client_id;
    private Long company_id;

    public Deposit(RegisterDepositData data, Long clientId, Long companyId, Double tax) {
        this.value = data.value();
        this.tax = tax;
        this.client_id = clientId;
        this.company_id = companyId;
    }
}
