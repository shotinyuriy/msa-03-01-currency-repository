package msa.course.currency.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="currency_rate", uniqueConstraints = {
        @UniqueConstraint(name = "currency_at_timestamp", columnNames = {"currency", "timestamp"})
})
@Getter
@Setter
@NoArgsConstructor
public class CurrencyRate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    private LocalDateTime timestamp;

    private BigDecimal rate;
}
