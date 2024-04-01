package tgid.transactional.transactionalService.withdraw;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
}
