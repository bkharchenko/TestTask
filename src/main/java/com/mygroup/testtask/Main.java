package com.mygroup.testtask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author bodya
 */

public class Main {
    public static void main(String args[]){
        
        System.out.println("Please type your query here:");
        //Считываем запрос с консоли
        Scanner in = new Scanner(System.in);
        final String query = in.nextLine();
        
        //Подключаем Веб-Драйвер для FireFox
        WebDriver ffd = new FirefoxDriver();
        
        //Переходим на стартовую страницу Гугл
        ffd.get("https://www.google.com.ua/");
        
        //Ищем элемент по заданому id, вставляем туда запрос, сабмитим
        WebElement el = ffd.findElement(By.id("lst-ib"));
        el.sendKeys(query);
        el.submit();
        
        //Ожидаем пока загрузится страница
        (new WebDriverWait(ffd, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().startsWith(query);
            }
        });
        
        //Сохраняем список всех результатов первой страницы (результаты находим через запрос xpath)
        List<WebElement> ElementList = ffd.findElements(By.xpath(".//*[@id='rso']/div[@class='srg']/li/div/h3/a"));
        
        //Делаем новый список уэе самих ссылок на результаты
        ArrayList<String> LinkList = new ArrayList<String>();
        for (int i = 0; i < ElementList.size(); i++){
            LinkList.add(ElementList.get(i).getAttribute("href"));
        }
        
        System.out.println("Ok, here is relust list:");
        //Проходим по каждому результату, выводим в консоль заголовок страницы
        for (int i = 0; i < LinkList.size(); i++){
            ffd.get(LinkList.get(i));
            System.out.println(ffd.getTitle());
        }
    }
}
