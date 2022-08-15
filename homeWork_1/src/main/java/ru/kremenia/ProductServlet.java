package ru.kremenia;

import ru.kremenia.persist.Product;
import ru.kremenia.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final Pattern PATTERN = Pattern.compile("\\/(\\d+)");
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        // Создание репозитория в единственном экземпляре
        this.productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getPathInfo() == null || req.getPathInfo().equals("/")){
            PrintWriter wr = resp.getWriter();
            wr.println("<table>");            // Создание таблицы
            wr.println("<tr>");               // Заголовок таблицы
            wr.println("<th>Id</th>");        // Создание поля ID
            wr.println("<th>ProductName</th>");  // Создание поля ProductName
            wr.println("</tr>");

            for (Product product : productRepository.findAll()) {
                wr.println("<tr>");
                wr.print("<td><a href='" + getServletContext().getContextPath() +"/product/" + product.getId() + "'>" + product.getId() + "</a></td>");
                wr.println("<td>" + product.getProduct() + "</td>");
                wr.println("</tr>");
            }

            wr.println("</table>");
        } else {
            Matcher matcher = PATTERN.matcher(req.getPathInfo());
            if (matcher.matches()) {
                long id = Long.parseLong(matcher.group(1));
                Product product = productRepository.findById(id);
                if (product == null) {
                    resp.getWriter().println("<p> Product not found</p>");
                    resp.setStatus(404);
                    return;
                }
                resp.getWriter().println("<p>Id: " + product.getId() + "</p>");
                resp.getWriter().println("<p>Id: " + product.getProduct() + "</p>");
            } else {
                resp.getWriter().println("<p>" + "Bad parameters" + " </p");
                resp.setStatus(400);
            }
        }

    }
}