package parsing;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ParseKufar {

	public static void main(String[] args) {
		
		getLinks("https://www.kufar.by/l/r~grodnenskaya-obl/velosipedy?query=%D0%B2%D0%B5%D0%BB%D0%BE%D1%81%D0%B8%D0%BF%D0%B5%D0%B4&sort=prc.a");

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
		
		try {
			//Поиск количества страниц и создание массива для их хранения
			List<WebElement> pages = webDriver.findElements(By.className("styles_link__KajLs"));
			int lastPage = Integer.parseInt(pages.get(pages.size() - 2).getText());
			System.out.println("There are " + lastPage + " pages");
			String[] links = new String[lastPage];
			
			int n = 1;
			for (int i = 0; i < lastPage; i++) {
				links[i] = webDriver.getCurrentUrl();
				pages = webDriver.findElements(By.className("styles_link__KajLs"));
				WebElement nextPage = pages.get(pages.size() - 1);
				nextPage.click();
				System.out.println("Current page is " + n++ + ". Next page!");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			int k = 1;
			for (String i : links) {
				System.out.println(k++ + " " + i);
			}
			
		} catch (NullPointerException e) { //Только 1 страница с товарами
			String[] links = new String[1];
			links[0] = webDriver.getCurrentUrl();
		}

		
		
		
	}

}
