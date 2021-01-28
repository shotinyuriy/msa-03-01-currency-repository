package msa.course.currency.repository;

import msa.course.currency.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    // SELECT * FROM  currency_rate cr1
    // JOIN (SELECT currency, MAX(timestamp) FROM currency_rate GROUP BY currency) as cr2
    //  ON cr1.currency = cr2.currency AND cr1.timestamp = cr2.timestamp
    @Query(value = "SELECT * FROM  currency_rate cr1 JOIN (SELECT currency, MAX(timestamp) as latest " +
            "FROM currency_rate GROUP BY currency) as cr2 " +
            "  ON cr1.currency = cr2.currency AND cr1.timestamp = cr2.latest", nativeQuery = true)
    List<CurrencyRate> findActualCurrencyRates();
}
