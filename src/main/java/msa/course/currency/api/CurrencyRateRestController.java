package msa.course.currency.api;

import lombok.RequiredArgsConstructor;
import msa.course.currency.api.dto.ResponseDto;
import msa.course.currency.entity.CurrencyRate;
import msa.course.currency.repository.CurrencyRateRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/currency-rates")
@RequiredArgsConstructor
public class CurrencyRateRestController {

    private final CurrencyRateRepository currencyRateRepository;

    @GetMapping
    public ResponseEntity<ResponseDto> getCurrencyRates() {
        return ResponseEntity.ok(ResponseDto.builder()
                .currencyRates(currencyRateRepository.findAll())
                .build());
    }

    @GetMapping("/actual")
    public ResponseEntity<ResponseDto> getActualCurrencyRates() {
        return ResponseEntity.ok(ResponseDto.builder()
                .currencyRates(currencyRateRepository.findActualCurrencyRates())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRate> getCurrencyRates(@PathVariable Long id) {

        return currencyRateRepository.findById(id)
                .map(currencyRate -> ResponseEntity.ok(currencyRate))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createCurrencyRate(@RequestBody CurrencyRate currencyRate) {
        try {
            CurrencyRate savedCurrencyRate = currencyRateRepository.save(currencyRate);
            Link link = linkTo(methodOn(CurrencyRateRestController.class).getCurrencyRates(savedCurrencyRate.getId())).withSelfRel();

            return ResponseEntity
                    .created(link.toUri())
                    .body(ResponseDto.builder().link(link.getHref()).build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().errorMessage(e.getMessage()).build());
        }
    }
}
