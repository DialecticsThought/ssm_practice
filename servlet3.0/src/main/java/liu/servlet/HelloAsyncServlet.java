package liu.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/async",asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    public void sayHello () throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"processing....");
        Thread.sleep(3000);
    }
    /*
    *request到服务端 一开始由主线程池的主线程处理
    * 发现是要异步处理的 立即获取AsyncContext
    *  AsyncContext在交给异步线程池的异步线程处理
    *  主线程交给其他以后 自己处理主线程要处理的业务
    * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.支持异步处理asyncSupported = true
        //2.开启异步处理模式
        System.out.println("主线程开始..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());
        AsyncContext startAsync = req.startAsync();
        //3.业务逻辑进行业务处理 开始异步处理
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("子线程开始..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());
                    sayHello();
                    startAsync.complete();
                    //4.异步结束
                    //5.获取异步上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    //6.获取响应
                    ServletResponse response = asyncContext.getResponse();
                    //响应体输出
                    response.getWriter().write("hello async.....");

                    System.out.println("子线程结束..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
