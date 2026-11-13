package com.eliasnogueira.paymentservice.unit;

import com.eliasnogueira.paymentservice.dto.PaymentRequest;
import com.eliasnogueira.paymentservice.exceptions.PaymentLimitException;
import com.eliasnogueira.paymentservice.model.enums.PaymentSource;
import com.eliasnogueira.paymentservice.repository.PaymentRepository;
import com.eliasnogueira.paymentservice.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("Should save the payment when daily limit is not reached")
    void shouldSavePaymentWhenAmountIsNotExceedDailyLimit() {
        when(paymentRepository.sumPaymentsByPayerIdAndDate(any(), any(), any()))
                .thenReturn(new BigDecimal("200.00"));

        when(paymentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var paymentRequest = PaymentRequest.builder()
                .payerId(UUID.randomUUID())
                .paymentSource(PaymentSource.CREDIT_CARD)
                .amount(BigDecimal.valueOf(200.00)).build();

        var savedPayment = paymentService.createPayment(paymentRequest);

        assertThat(savedPayment.getPayerId()).isEqualTo(paymentRequest.getPayerId());
        verify(paymentRepository).save(any());
    }

    @Test
    @DisplayName("Should thrown an exception when the daily limit exceed")
    void shouldThrownExceptionWhenAmountExceedDailyLimit() {
        when(paymentRepository.sumPaymentsByPayerIdAndDate(any(), any(), any()))
                .thenReturn(new BigDecimal("8000.00"));

        var paymentRequest = PaymentRequest.builder().payerId(UUID.randomUUID()).paymentSource(PaymentSource.CREDIT_CARD)
                .amount(BigDecimal.valueOf(2100.00)).build();

        assertThatThrownBy(() -> paymentService.createPayment(paymentRequest))
                .isInstanceOf(PaymentLimitException.class)
                .hasMessageContaining("Daily payment limit exceeded");
    }

    @Test
    void naoPodeVotar(){
        assertThat(podeVotar(16)).isFalse();

    }

    @ParameterizedTest(name = "Pode votar com idade = {0}")
    @ValueSource(ints = {18, 20, 60, 70})
    void valueSourceTest(int idade) {
        assertThat(podeVotar(idade)).isTrue();

    }

    public boolean podeVotar(int idade) {
        boolean retorno= false;
        if (idade >=18 && idade <= 70) {
            return true;
        }
        return retorno;
    }


    private static final String MAX_LIMIT = "30.0";

    @ParameterizedTest(name= "O Produto {0} no valor de {1} deve ser menor que "+ MAX_LIMIT)
    @CsvSource({
            "Micro sd, 6.9",
            "JBL, 28.8"
    })
    void produtoMenoresDe30(String produto, BigDecimal valor) {
        assertThat(produto).isNotEmpty();
        assertThat(valor).isLessThanOrEqualTo(new BigDecimal(MAX_LIMIT));
    }


    @ParameterizedTest(name= "O Produto {0} no valor de {1} deve ser menor que "+ MAX_LIMIT)
   @CsvFileSource(resources = "/produtos.csv", numLinesToSkip = 1)
    void produtoMenoresDe30FileSource(String produto, BigDecimal valor) {
        assertThat(produto).isNotEmpty();
        assertThat(valor).isLessThanOrEqualTo(new BigDecimal(MAX_LIMIT));
    }

}
