/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.Bean.MessageBean;
import com.neu.Bean.UsersBean;
import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Rajat
 */
public class MessageController extends AbstractController {

    public MessageController() {
    }

    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        DataSource ds = (DataSource) this.getApplicationContext().getBean("myDataSource");

        String action = request.getParameter("action");
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        if (action.equalsIgnoreCase("reply")) {
            
            try{
                
            String receiver = request.getParameter("to");
             System.out.println("Printing receiver in reply case: "+ receiver);
            
            
            QueryRunner run = new QueryRunner(ds);
            ResultSetHandler<UsersBean> user = new BeanHandler<UsersBean>(UsersBean.class);
            UsersBean ub = run.query("select * from userstable where userName =?", user, receiver);
            if (ub != null) {
                System.out.println("printing userEmail received from DB: "+ ub.getUserEmail());
                
                mv.addObject("toEmail", ub.getUserEmail());
                mv.addObject("to", receiver);
            }

            mv.setViewName("reply");

            
            }catch(SQLException e)
            {
                System.out.println(e);
            
            }
            
            
        } else if (action.equalsIgnoreCase("sent")) {
            System.out.println("In sent case");
            
            try {
                String receiver = request.getParameter("to");
                String receiverEmail = request.getParameter("toEmail");
                
                System.out.println("printing receiver email: "+receiverEmail);
                
                QueryRunner run = new QueryRunner(ds);
                ResultSetHandler<UsersBean> user = new BeanHandler<UsersBean>(UsersBean.class);
                UsersBean ub = run.query("select * from userstable where userName =?", user, userName);
                if (ub != null) {
                    String senderEmail = ub.getUserEmail();
                     System.out.println("printing senderemail: "+ senderEmail);
                    
                    ResultSetHandler<MessageBean> msg = new BeanHandler<MessageBean>(MessageBean.class);
                    Object[] params = new Object[4];
                    params[0] = userName;
                    params[1] = request.getParameter("message");
                    Date d = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String messageDate = format.format(d);
                    params[2] = messageDate;
                    params[3] = receiver;
                    int inserts = run.update("Insert into messages (fromUser,message,messageDate,userName) values(?,?,?,?)", params);//Logic to send the email

                    try {
                        Email email = new SimpleEmail();
                        email.setHostName("smtp.googlemail.com");//If a server is capable of sending email, then you don't need the authentication. In this case, an email server needs to be running on that machine. Since we are running this application on the localhost and we don't have a email server, we are simply asking gmail to relay this email.
                        email.setSmtpPort(465);
                        email.setAuthenticator(new DefaultAuthenticator("contactapplication2017@gmail.com", "springmvc"));
                        email.setSSLOnConnect(true);
                        email.setFrom(senderEmail);//This email will appear in the from field of the sending email. It doesn't have to be a real email address.This could be used for phishing/spoofing!
                        email.setSubject("Thanks for Signing Up!");
                        email.setMsg("Welcome to Web tools Lab 5 Spring Application sign up email test!");
                        email.addTo(receiverEmail);//Will come from the database
                        email.send();
                    } catch (Exception e) {
                        System.out.println("Email Exception" + e.getMessage());
                        e.printStackTrace();
                    }
                    mv.setViewName("messageSent");
                } else {
                    mv.addObject("error", "true");
                    mv.setViewName("index");

                }

            } catch (Exception ex) {
                System.out.println("Error Message" + ex.getMessage());
                ex.printStackTrace();

            }

        }

        return mv;
    }

}
