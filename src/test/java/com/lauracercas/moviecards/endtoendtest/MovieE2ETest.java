package com.lauracercas.moviecards.endtoendtest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.lauracercas.moviecards.util.Messages.NEW_MOVIE_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
public class MovieE2ETest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    public void testPageLoadAndForm() {
        driver.get("http://localhost:8089/movies/new");
        assertEquals("FichasPeliculasApp | Aplicación de gestión de fichas de películas", driver.getTitle());

        assertTrue(driver.findElement(By.id("title")).isDisplayed());
        assertTrue(driver.findElement(By.id("year")).isDisplayed());
        assertTrue(driver.findElement(By.id("duration")).isDisplayed());
        assertTrue(driver.findElement(By.id("country")).isDisplayed());
        assertTrue(driver.findElement(By.id("director")).isDisplayed());
        assertTrue(driver.findElement(By.id("genre")).isDisplayed());
        assertTrue(driver.findElement(By.id("sinopsis")).isDisplayed());
    }

    @Test
    public void testNewMovieTitle() {
        driver.get("http://localhost:8089/movies/new");
        WebElement title = driver.findElement(By.className("title"));
        assertEquals(NEW_MOVIE_TITLE, title.getText());
    }

    @Test
    public void testListMovies() {
        driver.get("http://localhost:8089/movies");
        WebElement title = driver.findElement(By.className("card-header"));
        assertEquals("Listado Peliculas", title.getText());

        WebElement table = driver.findElement(By.className("table-hover"));

        WebElement thead = table.findElement(By.tagName("thead"));
        assertTrue(thead.isDisplayed());

        WebElement headerRow = thead.findElement(By.tagName("tr"));
        assertEquals("Identificador", headerRow.findElements(By.tagName("th")).get(0).getText());
        assertEquals("Titulo", headerRow.findElements(By.tagName("th")).get(1).getText());
        assertEquals("Año", headerRow.findElements(By.tagName("th")).get(2).getText());
        assertEquals("Duración", headerRow.findElements(By.tagName("th")).get(3).getText());
        assertEquals("País", headerRow.findElements(By.tagName("th")).get(4).getText());
        assertEquals("Dirección", headerRow.findElements(By.tagName("th")).get(5).getText());
        assertEquals("Género", headerRow.findElements(By.tagName("th")).get(6).getText());
        assertEquals("Sinopsis", headerRow.findElements(By.tagName("th")).get(7).getText());
        assertEquals("Editar", headerRow.findElements(By.tagName("th")).get(8).getText());

    }

}
