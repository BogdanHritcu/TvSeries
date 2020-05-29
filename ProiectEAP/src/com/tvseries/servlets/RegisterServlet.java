package com.tvseries.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tvseries.dao.UserInfoDAO;
import com.tvseries.utils.PasswordChecker;
import javafx.util.Pair;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.GenericValidator;

public class RegisterServlet extends HttpServlet
{
    //TODO: validate email, check if username/email is unique
    public boolean validatePassword(String pass, String cpass)
    {
        return (!GenericValidator.isBlankOrNull(pass) && pass.length() >= 5 && pass.length() <= 40 && pass.equals(cpass) && PasswordChecker.check(pass));
    }

    public boolean validateName(String name)
    {
        return (!GenericValidator.isBlankOrNull(name) && name.length() >= 3 && name.length() <= 30 && PasswordChecker.check(name));
    }

    public String processPassword(String pass)
    {
        String hashpass = DigestUtils.sha256Hex(pass.getBytes());

        return hashpass;
    }

    public Map<String, String> validateCredentials(String user, String disp, String email, String pass, String cpass)
    {
        Map<String, String> messages = new HashMap<String, String>();

        if(!validateName(user))
        {
            messages.put("username", "Invalid username");
        }

        if(!validateName(disp))
        {
            messages.put("display", "Invalid display name");
        }

        if(GenericValidator.isEmail(email))
        {
            messages.put("email", "Invalid email");
        }

        if(!validatePassword(pass, cpass))
        {
            messages.put("password", "Invalid password");
        }

        return messages;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String display_name = req.getParameter("display_name");
        String email = req.getParameter("email");
        String password = req.getParameter("pass");
        String cpassword = req.getParameter("cpass");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String bdate_str = req.getParameter("bdate");

        LocalDate bdate = null;
        if(GenericValidator.isDate(bdate_str, "yyyy-MM-dd", true))
        {
            bdate = LocalDate.parse(bdate_str, DateTimeFormatter.ISO_LOCAL_DATE);
        }

        if(GenericValidator.isBlankOrNull(display_name))
        {
            display_name = username;
        }

        Map<String, String> messages = validateCredentials(username, display_name, email, password, cpassword);

        HttpSession session = req.getSession();

        if(messages.isEmpty()) //if there are no error messages
        {
            String hashpass = processPassword(password);

            List<Pair<String, Object>> attributes = new ArrayList<>();
            attributes.add(new Pair<>("username", username));
            attributes.add(new Pair<>("password", hashpass));
            attributes.add(new Pair<>("display_name", display_name));
            attributes.add(new Pair<>("email", email));
            attributes.add(new Pair<>("first_name", fname));
            attributes.add(new Pair<>("last_name", lname));
            attributes.add(new Pair<>("birth_date", bdate));

            try
            {

                boolean updated = UserInfoDAO.addUser(attributes);
                session.setAttribute("username", username);
                session.setAttribute("display_name", display_name);
                res.sendRedirect(req.getContextPath() + "/index.jsp");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            req.setAttribute("messages", messages); //send error messages
            req.getRequestDispatcher("/register.jsp").forward(req, res);
        }
    }
}
