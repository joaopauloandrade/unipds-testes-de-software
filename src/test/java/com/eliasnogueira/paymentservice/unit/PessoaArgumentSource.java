package com.eliasnogueira.paymentservice.unit;


import com.eliasnogueira.paymentservice.Pessoa;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class PessoaArgumentSource implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.arguments(new Pessoa("João",20), "Pode Votar"),
                Arguments.arguments(new Pessoa("Maria",25), "Pode Votar"),
                Arguments.arguments(new Pessoa("Andre",49), "Pode Votar")
        );
    }
}
