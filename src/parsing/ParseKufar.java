package parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ParseKufar {

	public static void main(String[] args) {
		
		getLinks("https://www.kufar.by/l");

	}
	
	public static void getLinks(String URL) {
		
		System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(URL);
		
		WebElement popupWindows;
		try {
			//Нажимаем на принятие cookie
			popupWindows = webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div/div[2]/button"));
			popupWindows.click();
			//Закрываем рекламу
			popupWindows = webDriver.findElement(By.xpath("//*[@id=\"portal\"]/div/div[2]/div[1]/div/button"));
			popupWindows.click();
		} catch (NoSuchElementException e) { //Элемент рекламы не успел загрузиться, ждем
			try {
				System.out.println("No such element and you have to wait!");
				Thread.sleep(500);
				popupWindows = webDriver.findElement(By.xpath("//*[@id=\"portal\"]/div/div[2]/div[1]/div/button"));
				popupWindows.click();
				System.out.println("Sucsess!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (ElementNotInteractableException e) { //Неудача при первой попытке закрыть рекламу
			System.out.println("One more try to click!");
			popupWindows = webDriver.findElement(By.xpath("//*[@id=\"portal\"]/div/div[2]/div[1]/div/img"));
			popupWindows.click();
			System.out.println("Sucsess!");
		}

		
	}

}
