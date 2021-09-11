package liu.controller;

import liu.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

@Controller
public class AsyncController {
    /*
    * 1.控制器返回的是Callable
    * 2.SpringMVC会把Callable提交到 TaskExecutor来处理 使用一个隔离的线程进行之星
    * 3.DispatcherServlet和所有的filter都会退出web容器的线程 但是response保持打开状态 说明可以在请求里面端写数据
    * 4.Callable返回结果 SpringMVC重新把请求发给容器 恢复之前的处理
    * 5.根据Callable返回的结果 SpringMVC继续视图渲染等（但是原先的目标方法不会再次执行 因为Callable返回的结值就是目标方法的返回值）
    * （从头开始收请求 视图渲染等。。。。 因为是相当于请求重新发送了一遍 如果有interceptor的话 发现interceptor执行了2次）
    *
    * terminal
        preHandle
        主线程开始...http-nio-8080-exec-4===>1630763103161
        主线程结束...http-nio-8080-exec-4===>1630763103163
        =================DispatcherServlet和所有的filter都会退出线程====================
        21:45:03.165 [http-nio-8080-exec-4] WARN org.springframework.web.context.request.async.WebAsyncManager -
        !!!
        An Executor is required to handle java.util.concurrent.Callable return values.
        Please, configure a TaskExecutor in the MVC config under "async support".
        The SimpleAsyncTaskExecutor currently in use is not suitable under load.
        -------------------------------
        Request URI: '/SpringMVC_annotation02/async01'
        !!!
        21:45:03.167 [http-nio-8080-exec-4] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Started async request

        ============================等待Callable执行=========================================

        子线程开始...MvcAsync1===>1630763103168

        21:45:03.169 [http-nio-8080-exec-4] DEBUG org.springframework.web.servlet.DispatcherServlet - Exiting but response remains open for further handling
        04-Sep-2021 21:45:04.964 信息 [Catalina-utility-1] org.apache.catalina.startup.HostConfig.deployDirectory 把web 应用程序部署到目录 [E:\apache-tomcat-9.0.41\webapps\manager]
        04-Sep-2021 21:45:04.989 信息 [Catalina-utility-1] org.apache.catalina.startup.HostConfig.deployDirectory Web应用程序目录[E:\apache-tomcat-9.0.41\webapps\manager]的部署已在[24]毫秒内完成
        子线程结束...MvcAsync1===>1630763106178

        ============================等待Callable执行结束=========================================

        21:45:06.178 [MvcAsync1] DEBUG org.springframework.web.context.request.async.WebAsyncManager - Async result set, dispatch to /SpringMVC_annotation02/async01
        21:45:06.179 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.DispatcherServlet - "ASYNC" dispatch for GET "/SpringMVC_annotation02/async01", parameters={}
        ============================Callable返回结果 SpringMVC重新把请求发给容器=======================

        21:45:06.180 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Resume with async result ["Callable<String> async01()"]
        21:45:06.192 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor - Using 'text/html', given [text/html, application/xhtml+xml, image/webp, image/apng, application/xml;q=0.9, application/signed-exchange;v=b3;q=0.9, **;q=0.8] and supported [text/plain, **, application/json, application/*+json]
        21:45:06.192 [http-nio-8080-exec-5] DEBUG org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor - Writing ["Callable<String> async01()"]
        postHandle（但是原先的目标方法不会再次执行 因为Callable返回的结值就是目标方法的返回值）
        afterCompletion



        * request到服务端 一开始由主线程池的主线程处理
        * 发现是要异步处理的 立即获取AsyncContext
        * AsyncContext在交给异步线程池的异步线程处理
        * 主线程交给其他以后 自己处理主线程要处理的业务
    *
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
    */
    @ResponseBody
    @RequestMapping("async01")
    public Callable<String> async01(){
        System.out.println("主线程开始..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());

        Callable<String> callable = new Callable<>() {

            @Override
            public String call() throws Exception {
                System.out.println("子线程开始..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());

                Thread.sleep(3000);

                System.out.println("子线程结束..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());

                return "Callable<String> async01()";
            }
        };
        System.out.println("主线程结束..."+Thread.currentThread().getName()+"===>"+System.currentTimeMillis());
        return callable;
    }

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder(){
        //超过3秒 就失败
        DeferredResult<Object> objectDeferredResult = new DeferredResult<>((long)3000,"create fail....");

        DeferredResultQueue.save(objectDeferredResult);

        return objectDeferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        //创建订单
        String order = UUID.randomUUID().toString();
        //相当于另外一个线程 获得这个任务
        DeferredResult<Object> objectDeferredResult = DeferredResultQueue.get();
        //完成这个任务 并返回结果
        objectDeferredResult.setResult(order);


        return "success ===>"+order;
    }
}
