package com.mohai.one.springbootservlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FirstListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("FirstListener监听器正在初始化");
        System.out.println("servlet container："+sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("FirstListener监听器正在 销毁");
    }
}
