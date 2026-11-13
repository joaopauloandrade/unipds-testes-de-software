package com.eliasnogueira.paymentservice.unit;

import com.eliasnogueira.paymentservice.Pessoa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

class ArgumentSourceTest {


    @ParameterizedTest
    @ArgumentsSource(PessoaArgumentSource.class)
    void podeVotar(Pessoa pessoa , String resultado) {
        Assertions.assertThat(pessoa.getIdade()).isGreaterThanOrEqualTo(18).isLessThanOrEqualTo(70);
        Assertions.assertThat(resultado).isEqualTo("Pode Votar");
    }
}
