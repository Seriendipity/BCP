package com.example.bcp.utils;

import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse)  response;

        res.setCharacterEncoding("UTF-8");

        //获取header的token
        final String token = ((HttpServletRequest) request).getHeader("Authorization");
       // System.out.println(token);
        if("OPTIONS".equals(req.getMethod())){
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req,res);
        }else{
            if(token == null){
                res.getWriter().write("No Token");
                return;
            }
            Map<String, Claim> userData = JwtUtils.verifyToken(token);
            if(userData == null){
                res.getWriter().write("Token is illegal");
                return;
            }
            // 根据不同的路径，执行不同的逻辑
            String path = req.getRequestURI();
            if(path.startsWith("/secure/getUserInfo")){
                String userName = userData.get("userName").asString();
                String password = userData.get("password").asString();

                System.out.println("UserData: "+userName+ " " + password);
                System.out.println("---------------------------------");
                //拦截器，拿到用户信息，放到req中
                req.setAttribute("username",userName);
                req.setAttribute("password",password);

            }
            if(path.startsWith("/comment/*")){
                System.out.println("调用Comment/拦截器");
            }
            if(path.startsWith("/discussion/*")){
                System.out.println("调用Discussion/拦截器");
            }
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
