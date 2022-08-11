package ru.kremenia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = new ProductRepository();
        productRepository.insert(new Product("Product 1"));
        productRepository.insert(new Product("Product 2"));
        productRepository.insert(new Product("Product 3"));
        productRepository.insert(new Product("Product 4"));
        productRepository.insert(new Product("Product 5"));
        productRepository.insert(new Product("Product 6"));
        productRepository.insert(new Product("Product 7"));
        productRepository.insert(new Product("Product 8"));
        productRepository.insert(new Product("Product 9"));
        productRepository.insert(new Product("Product 10"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        wr.println("<table>");            // Создание таблицы
        wr.println("<tr>");               // Заголовок таблицы
        wr.println("<th>Id</th>");        // Создание поля ID
        wr.println("<th>ProductName</th>");  // Создание поля ProductName
        wr.println("</tr>");

        for (Product product : productRepository.findAll()) {
            wr.println("<tr>");
            wr.print("<td><a href='" + getServletContext().getContextPath() +"/product" + product.getId() + "'>" + product.getId() + "</a></td>");
            wr.println("<td>" + product.getProduct() + "</td>");
            wr.println("</tr>");
        }

        wr.println("</table>");
    }
}
