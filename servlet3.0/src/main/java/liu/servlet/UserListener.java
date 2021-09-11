package liu.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UserListener implements ServletContextListener {
    //监听ServletContext监听初始化
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("userListener.... contextInitialized....");
    }
    //监听ServletContext监听销毁
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("userListener....contextDestroyed....");
    }
}
