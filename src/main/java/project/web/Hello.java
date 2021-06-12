package project.web;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class Hello extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String commandLane = req.getParameter("command");
        System.out.println(req.getSession());
        System.out.println(req.getServletContext());
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {


    }
}
