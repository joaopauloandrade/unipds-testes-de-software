package com.eliasnogueira.paymentservice.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class GovBrLoginTest {

    @Container
    private final BrowserWebDriverContainer<?> chromeContainer =
            new BrowserWebDriverContainer<>(DockerImageName.parse("selenium/standalone-chrome:latest"))
                    .withCapabilities(getChromeOptions());

    private RemoteWebDriver driver;

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        // Argumentos úteis para mitigar detecção básica em ambientes automatizados
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        return options;
    }

    @BeforeEach
    public void setUp() {
        driver = chromeContainer.getWebDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testGovBrLoginPageLoad() {
        // 1. Acessa a página oficial de login do gov.br
        driver.get("https://sso.acesso.gov.br/");

        // 2. Valida se o campo de CPF está presente na tela inicial
        WebElement cpfInput = driver.findElement(By.id("accountId"));
        assertTrue(cpfInput.isDisplayed(), "O campo de CPF deveria estar visível na tela de login do gov.br");

        // 3. Exemplo de preenchimento do CPF (Apenas para fins estruturais de teste)
         cpfInput.sendKeys("78644852515");

         WebElement botaoContinuar = driver.findElement(By.id("enter-account-id"));
         botaoContinuar.click();

        // OBSERVAÇÃO: A partir daqui, o fluxo exigirá preenchimento de senha e,
        // muito provavelmente, validações complexas de CAPTCHA ou duplo fator (MFA).
        WebElement password = driver.findElement(By.id("password"));
        assertTrue(password.isDisplayed(), "O campo de password está presente");

    }
}
