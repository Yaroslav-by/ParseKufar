# ParseKufar

<h3 align="center">Parse Kufar</h3>

  <p align="center">
    This project is about parsing Kufar.by (The site is constantly changing, and there is no time to constantly maintain the application, so now it is hardly functional)
  </p>
</div>


<!-- ABOUT THE PROJECT -->
## About The Project

It looks like something like this. You should write what you want to find, then you can choose category and region. Then you press "Find" and wait.<br><br>
<a href="https://ibb.co/H2LSxCm"><img src="https://i.ibb.co/CtpcM6x/c77abc2d-2e69-43b7-9570-2120fbd9d4ce.png" alt="c77abc2d-2e69-43b7-9570-2120fbd9d4ce" border="0" /></a>
<br>
<br>
The principle of operation is the following. After pressing the search button, a URL is generated. 
Next, Selenium launches Chrome and navigates to the received URL. Closes all pop-up ads and gets the number of pages with the product. 
Each product URL is stored in an array. After scrolling through the pages, the program, using the JSoap library, visits each URL with products and adds a 
link to each product in the HashSet. And at the last step, from the page of each product, we get information about it and display this data in a table


### Built With

* Java
* Selenium

### What could be done

* Add implementation via telegram
* Use Spring Framework
* Add Database to store items
* Add some filters

