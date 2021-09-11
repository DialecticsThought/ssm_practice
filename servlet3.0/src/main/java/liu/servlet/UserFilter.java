package liu.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //过滤请求
        System.out.println("userFilter..doFilter....");
        //放行
        chain.doFilter(request,response);
    }
}
