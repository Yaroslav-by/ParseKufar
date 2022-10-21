package parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextArea;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ParseKufar {
	
	private String[] links;
	
	public String[] getLinks(String URL, JTextArea textArea) {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\selenium\\chromedriver.exe");
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
				textArea.setText(textArea.getText() + "\n No such element and you have to wait!");
				Thread.sleep(500);
				popupWindows = webDriver.findElement(By.xpath("//*[@id=\"portal\"]/div/div[2]/div[1]/div/button"));
				popupWindows.click();
				System.out.println("Sucsess!");
				textArea.setText(textArea.getText() + "\n Sucsess!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (NoSuchElementException e1) { //Рекламу убрали с сайта
				System.out.println("Oops, the ad is not found!");
				textArea.setText(textArea.getText() + "\n Oops, the ad is not found!");
			}
		} catch (ElementNotInteractableException e) { //Неудача при первой попытке закрыть рекламу
			System.out.println("One more try to click!");
			textArea.setText(textArea.getText() + "\n One more try to click!");
			popupWindows = webDriver.findElement(By.xpath("//*[@id=\"portal\"]/div/div[2]/div[1]/div/img"));
			popupWindows.click();
			System.out.println("Sucsess!");
			textArea.setText(textArea.getText() + "\n Sucsess!");
		}
		
		try {
			//Поиск количества страниц и создание массива для их хранения
			List<WebElement> pages = webDriver.findElements(By.className("styles_link__KajLs"));
			int lastPage = Integer.parseInt(pages.get(pages.size() - 2).getText());
			links = new String[lastPage];
			
			System.out.println("There are " + lastPage + " pages. Starting to get links.");
			textArea.setText(textArea.getText() + "\n There are " + lastPage + " pages. Starting to get links.");
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
			textArea.setText(textArea.getText() + "\n Links added.");
			webDriver.quit();
			return links;

//			int k = 1;
//			for (String i : links) {
//				System.out.println(k++);
//				System.out.println(i);
//			}
			
		} catch (IndexOutOfBoundsException e) { //Только 1 страница с товарами
			links = new String[1];
			links[0] = webDriver.getCurrentUrl();
			textArea.setText(textArea.getText() + "\n Links added.");
			System.out.println("Links added.");
			webDriver.quit();
			return links;
		} catch (ElementClickInterceptedException e) {
			System.out.println("Advertising breaks plans, we take what we have.");
			textArea.setText(textArea.getText() + "\n Advertising breaks plans, we take what we have.");
			webDriver.quit();
			return links;
		}
	
	}

	public HashSet<String> getGoods(String[] links, JTextArea textArea) {
		
		Set<String> linksToGoods = new HashSet<String>(); //Хранение ссылок на товары
		Document document; 
		Elements goods;
		
		System.out.println("Starting to get goods.");
		textArea.setText(textArea.getText() + "\n Starting to get goods.");
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
		textArea.setText(textArea.getText() + "\n Finish. There are " + linksToGoods.size() + " goods.");
		return (HashSet<String>) linksToGoods;
		
//		int k = 1;
//		for (String i : linksToGoods) {
//			System.out.println(k++ + " " + i);
//		}
	
	}
	
	public ArrayList<HashMap<String, String>> getInfoFromGoods(HashSet<String> linksToGoods, JTextArea textArea) {
		
		List<HashMap<String, String>> information = new ArrayList<HashMap<String, String>>();
		Document document;
		
		System.out.println("Starting to get info from goods.");
		textArea.setText(textArea.getText() + "\n Starting to get info from goods.");
		for (int i = 0; i < linksToGoods.size(); i++) {
			
			//Подсчет количества пройденных товаров
			if (i % 50 == 0) {
				System.out.println("We're on " + i + " item!");
				textArea.setText(textArea.getText() + "\n We're on " + i + " item!");
			}

			HashMap<String, String> tempHashMap = new HashMap<String,String>();
			try {
				document = Jsoup.connect((String) linksToGoods.toArray()[i]).get();
				Element element;
				
				//Берем со страницы имя товара
				try {
					element = document.selectFirst("h1.styles_brief_wrapper__title__x59rm");
					tempHashMap.put("itemName", element.text());
				} catch (NullPointerException e) {
					tempHashMap.put("itemName", "Имя не найдено!");
				}

				//Берем со страницы цену товара
				try {
					element = document.selectFirst("span.styles_main__PU1v4");
					tempHashMap.put("itemPrice", element.text());
				} catch (NullPointerException e) {
					tempHashMap.put("itemPrice", "Цена не найдена!");
				}

				//Берем имя продавца товара
				try {
					element = document.selectFirst("div.styles_seller-block__top-right-name__imyGc");
					tempHashMap.put("sellerName", element.text());
				} catch (NullPointerException e) {
					tempHashMap.put("sellerName", "Имя не найдено!");
				}

				//Берем город продажи
				try {
					element = document.selectFirst("span.styles_address__fQyGA");
					tempHashMap.put("sellerCity", element.text());
				} catch (NullPointerException e) {
					tempHashMap.put("sellerCity", "Город не найден!");
				}

				//Берем ссылку на товар
				tempHashMap.put("itemLink", (String) linksToGoods.toArray()[i]);
				
				information.add(tempHashMap);
			} catch (HttpStatusException e) {
				System.out.println(e.getUrl() + " error fetching URL");
				textArea.setText(textArea.getText() + "\n error fetching URL");
			} catch (IOException e) {
				e.printStackTrace();
			} 
				
		}
		System.out.println("Done.");
		textArea.setText(textArea.getText() + "\n Done.");
		return (ArrayList<HashMap<String, String>>) information;
		
//		int k = 1;
//		for (HashMap<String, String> i : information) {
//			System.out.println(k++ + " " + i);
//		}
		
	}
	
}
