package com.demo;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class testLoginServlet {

    private static final String EXPECTED_MESSAGE = "login failed.";

    private HttpServletRequest request;
    private LoginServlet servlet;
    private ServletContext context;
    private RequestDispatcher dispatcher;

    private final String username;
    private final String password;

    public testLoginServlet(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Parameterized.Parameters(name = "{index}: username = {0}, password = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // {"admin", "123456"},

                {123456, "123456"},
                {"admin", 123456},
                {123456, 123456},
                {null, "123456"},
                {"admin", null},
                {null, null},
                {"admin"},
                {"admin", "123456","111"},
                {"adminadminadmin", "123456"},
                {"admin", "123456123456"},
                {"adminadminadmin", "123456123456"},
                {"ad", "123456"},
                {"admin", "123"},
                {"ad", "123"},
                {"*****", "123456"},
                {"admin", "******"},
                {"*****", "******"},
        });
    }

    @Before
    public void init() {
        request = EasyMock.createMock(HttpServletRequest.class);
        context = EasyMock.createMock(ServletContext.class);
        dispatcher = EasyMock.createMock(RequestDispatcher.class);
        servlet = new LoginServlet();
    }

    @Test
    public void testLoginFailed() throws Exception {
        EasyMock.expect(request.getParameter("username")).andReturn(username);
        EasyMock.expect(request.getParameter("password")).andReturn(password);
        EasyMock.expect(context.getNamedDispatcher("dispatcher")).andReturn(dispatcher);
        dispatcher.forward(request, null);
        EasyMock.replay(request);
        EasyMock.replay(context);
        EasyMock.replay(dispatcher);
        try {
            servlet.doPost(request, null);
            Assert.fail("Not caught exception!");
        } catch (Exception re) {
            Assert.assertEquals(EXPECTED_MESSAGE, re.getMessage());
            System.out.println("\tlogin failed.");
        }
        //??????mock???????????????
        EasyMock.verify(request);
    }

    @Test
    public void testLoginSucceed() throws Exception {
        EasyMock.expect(request.getParameter("username")).andReturn(username);
        EasyMock.expect(request.getParameter("password")).andReturn(password);
        EasyMock.expect(context.getNamedDispatcher("dispatcher")).andReturn(dispatcher);
        dispatcher.forward(request, null);
        EasyMock.replay(request);
        EasyMock.replay(context);
        EasyMock.replay(dispatcher);
        /*?????????getServletContext()?????????????????????ServletContext Mock?????????
         ??????????????????????????????getServletContext()??????*/
        LoginServlet servlet = new LoginServlet() {
            public ServletContext getServletContext() {
                return context;
            }
        };
        servlet.doPost(request, null);
        //??????mock???????????????
        EasyMock.verify(request);
        EasyMock.verify(context);
        EasyMock.verify(dispatcher);
    }
}