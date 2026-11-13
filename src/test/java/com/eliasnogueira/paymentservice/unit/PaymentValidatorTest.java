/*
 * MIT License
 *
 * Copyright (c) 2025 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.paymentservice.unit;

import com.eliasnogueira.paymentservice.validator.PaymentLimitValidator;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.selenium.BrowserWebDriverContainer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentValidatorTest {

    private static Faker faker = new Faker();

    @Test
    @DisplayName("Payment should be successful when amount is below the daily limit")
    void shouldAcceptAmountBelowTheLimit() {
        BigDecimal amount = new BigDecimal("1999.99");
        assertThat(PaymentLimitValidator.isWithinLimit(amount)).isTrue();
    }

    @Test
    @DisplayName("Payment should be successful when amount is below the daily limit")
    void shouldAcceptAmountBelowTheLimitfaker() {
        BigDecimal amount = BigDecimal.valueOf( faker.number().numberBetween(2,1999)); //new BigDecimal("1999.99");
        assertThat(PaymentLimitValidator.isWithinLimit(amount)).isTrue();
    }

    @Test
    @DisplayName("Payment should be successful when amount equal the daily limit")
    void shouldAcceptAmountEqualToTheLimit() {
        BigDecimal amount = new BigDecimal("2000.00");
        assertThat(PaymentLimitValidator.isWithinLimit(amount)).isTrue();
    }

    @Test
    @DisplayName("Payment should not be successful when amount is higher than the daily limit")
    void shouldNotAcceptAmountHigherThanTheLimit() {
        BigDecimal amount = new BigDecimal("2000.01");
        assertThat(PaymentLimitValidator.isWithinLimit(amount)).isFalse();
    }

    @Test
    @DisplayName("Payment should be successful when amount is null")
    void shouldNotAcceptANullValue() {
        assertThat(PaymentLimitValidator.isWithinLimit(null)).isFalse();
    }

    @Test
    void testSelenium() {
        System.out.println("Selenium test");
        var chrome = new BrowserWebDriverContainer("selenium/standalone-chrome:4.13.0");

        chrome.start();

        System.out.println("Selenium started");
    }

}
