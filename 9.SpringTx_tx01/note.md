声明式事务
    以前通过复杂的变成来编写的食物 替换为只需要告诉spring哪个方法是事务方法即可：
    Spring自动进行事务控制

aop：可以通过环绕通知来做到下面的事：
    //获取链接
    //设置非自动 提交
    目标代码执行
    //正常提交
    //异常回滚
    //最终关闭
最终效果
@Transactional
public void checkout(String username,String isbn){
//减库存
bookDao.updateStock(isbn);
//查价格
int price = bookDao.getPrice(isbn);
//减余额
bookDao.updateBalance(username,price);
}
事务管理代码的固定模式 作为一种横切关注点 可以通过aop方法的模块化 进而借助Spring AOP实现声明式事务

事务切面 叫做事务管理器


编程式事务
try{
//获取连接
//设置非自动 提交
chain.doFilter()
//提交
}catch(Exeception e){
//回滚
}finally{
//关闭连接 释放资源
}