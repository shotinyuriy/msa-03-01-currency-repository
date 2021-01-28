package msa.course.currency.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import msa.course.currency.entity.CurrencyRate;

import java.util.Collection;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private Collection<CurrencyRate> currencyRates;
    private String link;
    private String errorMessage;
}
