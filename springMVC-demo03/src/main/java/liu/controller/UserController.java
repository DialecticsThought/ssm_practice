package liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    /*
    * 使用restful风格实现用户资源的增删改查
    *查询操作 getUserById?id=1 user/1-->get请求方式
    保存操作 saveUser user-->post请求方式
    删除操作 deleteUser?id=1 user/1-->delete请求方式
    更新操作 updateUser user-->put请求方式
    *  /user  get 获取所有用户
    * /user/1 get 获取指定用户
    * /user  post 添加用户
    * /user/1 delete  删除用户信息
    * /user/1  put  更新用户信息
    * */
    @RequestMapping(value = "/user/{}",method = RequestMethod.GET)
    public String getAllUser(){
        System.out.println("查询所有用户信息");
        return "success";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据id查询用户信息");
        return "success";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String insertUser(String username,String password){
        System.out.println("添加用户信息"+username+","+password);
        return "success";
    }

    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String updateUser(String username,String password){
        System.out.println("更新用户信息"+username+","+password);
        return "success";
    }
}
