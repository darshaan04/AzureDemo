package com.example.auth;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
private JdbcUserDao dao;


@Override
public void init() throws ServletException {
try (InputStream is = getServletContext().getResourceAsStream("/WEB-INF/config.properties")) {
Properties p = new Properties();
p.load(is);
dao = new JdbcUserDao(p);
} catch (Exception e) {
throw new ServletException(e);
}
}


@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
}


@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
String username = req.getParameter("username");
String password = req.getParameter("password");
try {
var opt = dao.findByUsername(username);
if (opt.isPresent() && PasswordUtil.verifyPassword(password.toCharArray(), opt.get().passwordHash)) {
HttpSession s = req.getSession(true);
s.setAttribute("user", opt.get().username);
s.setAttribute("role", opt.get().role);
resp.sendRedirect(req.getContextPath() + "/secure");
} else {
req.setAttribute("error", "Invalid username or password");
req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
}
} catch (Exception e) {
throw new ServletException(e);
}
}
}