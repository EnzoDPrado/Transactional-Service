package tgid.transactional.transactionalService.withdraw;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
@Entity(name = "Withdraw")
@Table(name = "withdraw")
@EqualsAndHashCode(of = "id")
public class Withdraw {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;
    private Double tax;
    private Long client_id;
    private Long company_id;

    public Withdraw(RegisterWithdrawData data, Long clientId, Long companyId, double tax) {
        this.value = data.value();
        this.tax = tax;
        this.client_id = clientId;
        this.company_id = companyId;
    }
}
