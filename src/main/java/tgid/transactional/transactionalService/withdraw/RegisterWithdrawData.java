package tgid.transactional.transactionalService.withdraw;

import jakarta.validation.constraints.NotNull;

public record RegisterWithdrawData(
        @NotNull
        Double value
) { }
