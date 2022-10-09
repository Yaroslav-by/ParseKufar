package parsing;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		ParseKufar parseKufar = new ParseKufar();
		//String[] links = parseKufar.getLinks("https://www.kufar.by/l/r~orsha/lyustry?sort=lst.d");
		String[] test = {"https://www.kufar.by/l?query=%D0%BA%D0%BE%D1%80%D0%BE%D0%B2%D0%B0&sort=lst.d"};
		parseKufar.getGoods(test);
		
	}
	
	public String[] getLinks(String URL) {
		
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
			
//			int n = 1;
			for (int i = 0; i < lastPage; i++) {
				links[i] = webDriver.getCurrentUrl();
				pages = webDriver.findElements(By.className("styles_link__KajLs"));
				WebElement nextPage = pages.get(pages.size() - 1);
//				System.out.println("Current page is " + n++);
//				System.out.println(webDriver.getCurrentUrl());
//				System.out.println("Next page!");
//				System.out.println();
				nextPage.click();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
//			Этот костыль для получения URL 6 страницы, т.к. при пролистывании она пропускается
			if (lastPage > 6) {
				webDriver.get(links[4]);
				pages = webDriver.findElements(By.className("styles_link__KajLs"));
				WebElement nextPage = pages.get(pages.size() - 1);
				nextPage.click();
				links[links.length - 1] = webDriver.getCurrentUrl();
			}
			
			return links;

//			int k = 1;
//			for (String i : links) {
//				System.out.println(k++);
//				System.out.println(i);
//			}
			
		} catch (IndexOutOfBoundsException e) { //Только 1 страница с товарами
			String[] links = new String[1];
			links[0] = webDriver.getCurrentUrl();
			return links;
		}
	
	}

	public void getGoods(String[] links) {
		
		Set<String> linksToGoods = new HashSet<String>(); //Хранение ссылок на товары
		Document document; 
		Elements goods;
		
		for (int i = 0; i < links.length; i++) {
			try {
				document = Jsoup.connect(links[i]).get();
				goods = document.select("a.styles_wrapper__pb4qU");
				System.out.println(goods.size());
				int k = 1;
				for (Element product : goods) {
					System.out.println(k++ + " " + product.attr("href"));
					linksToGoods.add(product.attr("href"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
}
