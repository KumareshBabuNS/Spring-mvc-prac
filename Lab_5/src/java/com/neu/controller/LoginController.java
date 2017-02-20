/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.Bean.MessageBean;
import com.neu.Bean.UsersBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Rajat
 */
public class LoginController extends AbstractController {

    public LoginController() {
    }

    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        DataSource ds = (DataSource) this.getApplicationContext().getBean("myDataSource");

        String action = request.getParameter("action");
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();

        if (action.equalsIgnoreCase("login")) {
            try {
                String userName = request.getParameter("user");
                String password = request.getParameter("password");
                QueryRunner run = new QueryRunner(ds);
                ResultSetHandler<UsersBean> user = new BeanHandler<UsersBean>(UsersBean.class);
                Object[] params = new Object[2];
                params[0] = userName;
                params[1] = password;
                UsersBean ub = run.query("select * from userstable where userName =? and userPassword=?", user, params);
                if (ub != null) {
                    ResultSetHandler<List<MessageBean>> messages = new BeanListHandler<MessageBean>(MessageBean.class);
                    List<MessageBean> msg = run.query("select * from messages where userName =?", messages, userName);
                    session.setAttribute("userName", userName);
                    session.setAttribute("messageList", msg);
                    mv.setViewName("userhome");
                } else {
                    mv.addObject("error", "true");
                    mv.setViewName("index");

                }

            } catch (Exception ex) {
                System.out.println("Error Message" + ex.getMessage());

            }

        } else if (action.equalsIgnoreCase("logout")) {

            session.invalidate();
            mv.setViewName("index");
        }
        else if (action.equalsIgnoreCase("signup")) {

                System.out.println("sign up");
//                
//                String userName = request.getParameter("user");
//                String password = request.getParameter("password");
//                String emailObj = request.getParameter("emailObj");
//                
               // System.out.println("printing details: " + userName + " " +password + " "+emailObj);
                mv.setViewName("signup");
        }
        else if (action.equalsIgnoreCase("signupsubmit")) {

                System.out.println("sign up submit");
                
                String userName = request.getParameter("user");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                
                System.out.println("printing details: " + userName + " " +password + " "+email);
                
                    
                    
                    if(userName.equals("")||(password.equals(""))||(email.equals("")))
                    {
                        System.out.println("empty values");
                    mv.addObject("error", "true");
                    }
                   
                    else{
                        ResultSetHandler<UsersBean> user = new BeanHandler<UsersBean>(UsersBean.class);
                    Object[] params = new Object[3];
                    params[0] = userName;
                    params[1] = password;
                    params[2] = email;
                    QueryRunner run = new QueryRunner(ds);
                    int inserts = run.update("insert into userstable (UserName,UserPassword,UserEmail) values (?,?,?)", params);//Logic to insert into table
                    System.out.println("inserts value " + inserts );
                    
                    
                    if(inserts>0)
                    {
                    mv.addObject("success", "true");
                    Email emailObj = new SimpleEmail();
                        emailObj.setHostName("smtp.googlemail.com");//If a server is capable of sending emailObj, then you don't need the authentication. In this case, an emailObj server needs to be running on that machine. Since we are running this application on the localhost and we don't have a emailObj server, we are simply asking gmail to relay this emailObj.
                        emailObj.setSmtpPort(465);
                        emailObj.setAuthenticator(new DefaultAuthenticator("contactapplication2017@gmail.com", "springmvc"));
                        emailObj.setSSLOnConnect(true);
                        emailObj.setFrom("webtools@hello.com");//This emailObj will appear in the from field of the sending emailObj. It doesn't have to be a real emailObj address.This could be used for phishing/spoofing!
                        emailObj.setSubject("TestMail");
                        emailObj.setMsg("This is spring MVC Contact Application sending you the email");
                        emailObj.addTo(email);//Will come from the sign up details
                        emailObj.send();
                    }
                    
                    }
                    
                    
                    
                    
                    
                    
                    
                mv.setViewName("signup");
        }
        
        
        return mv;
    }

}
