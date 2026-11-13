package com.eliasnogueira.paymentservice.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class GoogleSearchTest {

    // Configura o container do navegador Chrome utilizando Testcontainers
    @Container
    private final BrowserWebDriverContainer<?> chromeContainer =
            new BrowserWebDriverContainer<>(DockerImageName.parse("selenium/standalone-chrome:latest"))
                    .withCapabilities(new org.openqa.selenium.chrome.ChromeOptions());

    private RemoteWebDriver driver;

    @BeforeEach
    public void setUp() {
        // Inicializa o WebDriver conectado ao container Docker em execução
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
    public void testGoogleSearch() {
        // 1. Acessa a página inicial do Google
        driver.get("https://www.google.com");

        // 2. Localiza a barra de pesquisa (name = "q")
        WebElement searchBox = driver.findElement(By.name("q"));

        // 3. Digita o termo de pesquisa e pressiona Enter
        searchBox.sendKeys("Testcontainers Selenium");
        searchBox.sendKeys(Keys.ENTER);

        // 4. Aguarda e valida se os resultados da pesquisa contêm o termo esperado
        // O Google possui diferentes estruturas de ID ou classes dependendo da região,
        // mas podemos validar se o título da página ou o corpo contém o termo buscado.
        boolean isResultPresent = driver.getTitle().toLowerCase().contains("testcontainers");

        System.out.println(driver.findElement(By.tagName("body")).getText());

        boolean isStringPresent = driver.findElement(By.tagName("body")).getText().contains("SeleniumContainerTest");

        assertTrue(isResultPresent, "O título da página deveria conter o termo pesquisado!");
        assertTrue(isStringPresent, "O conteudo foi encontrado!");
    }
}