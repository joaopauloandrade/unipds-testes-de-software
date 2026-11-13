package com.eliasnogueira.paymentservice.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MethodSourceTest {


    static Stream<Arguments> fonteDeDados() {
        return Stream.of(
                Arguments.arguments(18, "Pode Votar"),
                Arguments.arguments(70, "Pode Votar")
        );

    }


    @ParameterizedTest
    @MethodSource("fonteDeDados")
    void podeVotar(int idade, String resultado) {
        Assertions.assertThat(idade).isGreaterThanOrEqualTo(18).isLessThanOrEqualTo(70);
        Assertions.assertThat(resultado).isEqualTo("Pode Votar");
    }

    static class Pessoa {
        private String nome;
        private int idade;

        public Pessoa(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
        }

        public String getNome() {
            return nome;
        }
        public int getIdade() {
            return idade;
        }

        @Override
        public String toString() {
            return "Pessoa{" +
                    "nome='" + nome + '\'' +
                    ", idade=" + idade +
                    '}';
        }
    }

    static Stream<Arguments> fonteDeDadosPessoa() {
        return Stream.of(
                Arguments.arguments(new Pessoa("João",20), "Pode Votar"),
                Arguments.arguments(new Pessoa ("Andre",49), "Pode Votar")
        );

    }

    @ParameterizedTest
    @MethodSource("fonteDeDadosPessoa")
    void podeVotarPessoa(Pessoa pessoa, String resultado) {
        Assertions.assertThat(pessoa.getIdade()).isGreaterThanOrEqualTo(18).isLessThanOrEqualTo(70);
        Assertions.assertThat(resultado).isEqualTo("Pode Votar");
    }





}
