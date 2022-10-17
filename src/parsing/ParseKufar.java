package parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
		String[] links = parseKufar.getLinks("https://www.kufar.by/l/r~pinsk/velotovary?query=%D0%B2%D0%B5%D0%BB%D0%BE%D1%81%D0%B8%D0%BF%D0%B5%D0%B4&sort=lst.d");
		HashSet<String> linksToGoods = parseKufar.getGoods(links);
//		HashSet<String> testLinks = new HashSet<String>();
//		testLinks.add("https://www.kufar.by/item/171343513");
		parseKufar.getInfoFromGoods(linksToGoods);
		
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
			} catch (NoSuchElementException e1) { //Рекламу убрали с сайта
				System.out.println("Oops, the ad is not found!");
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
			String[] links = new String[lastPage];
			
			System.out.println("There are " + lastPage + " pages. Starting to get links.");
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
			
			System.out.println("Links added.");
			webDriver.quit();
			return links;

//			int k = 1;
//			for (String i : links) {
//				System.out.println(k++);
//				System.out.println(i);
//			}
			
		} catch (IndexOutOfBoundsException e) { //Только 1 страница с товарами
			String[] links = new String[1];
			links[0] = webDriver.getCurrentUrl();
			System.out.println("Links added.");
			webDriver.quit();
			return links;
		}
	
	}

	public HashSet<String> getGoods(String[] links) {
		
		Set<String> linksToGoods = new HashSet<String>(); //Хранение ссылок на товары
		Document document; 
		Elements goods;
		
		System.out.println("Starting to get goods.");
		for (int i = 0; i < links.length; i++) {
			try {
				document = Jsoup.connect(links[i]).get();
				goods = document.select("a.styles_wrapper__pb4qU");
				for (Element product : goods) {
					linksToGoods.add(product.attr("href"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Finish. There are " + linksToGoods.size() + " goods.");
		return (HashSet<String>) linksToGoods;
		
//		int k = 1;
//		for (String i : linksToGoods) {
//			System.out.println(k++ + " " + i);
//		}
	
	}
	
	public ArrayList<HashMap<String, String>> getInfoFromGoods(HashSet<String> linksToGoods) {
		
		List<HashMap<String, String>> information = new ArrayList<HashMap<String, String>>();
		Document document;
		
		System.out.println("Starting to get info from goods.");
		for (int i = 0; i < linksToGoods.size(); i++) {
			
			HashMap<String, String> tempHashMap = new HashMap<String,String>();
			try {
				document = Jsoup.connect((String) linksToGoods.toArray()[i]).get();
				Element element;
				
				//Берем со страницы имя товара
				element = document.selectFirst("h1.styles_brief_wrapper__title__x59rm");
				tempHashMap.put("itemName", element.text());
				
				//Берем со страницы цену товара
				element = document.selectFirst("span.styles_main__PU1v4");
				tempHashMap.put("itemPrice", element.text());
				
				//Берем имя продавца товара
				element = document.selectFirst("div.styles_seller-block__top-right-name__imyGc");
				tempHashMap.put("sellerName", element.text());
				
				//Берем город продажи
				element = document.selectFirst("span.styles_address__fQyGA");
				tempHashMap.put("sellerCity", element.text());
				
				//Берем ссылку на товар
				tempHashMap.put("itemLink", (String) linksToGoods.toArray()[i]);
				
				
				information.add(tempHashMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}
		System.out.println("Finish.");
		return (ArrayList<HashMap<String, String>>) information;
		
//		int k = 1;
//		for (HashMap<String, String> i : information) {
//			System.out.println(k++ + " " + i);
//		}
		
	}
	
}
