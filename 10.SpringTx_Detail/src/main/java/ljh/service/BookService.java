package ljh.service;

import ljh.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    /*
如果checkout和updatePrice  都是Propagation.REQUIRED 任意一个出错
或者  MulService出错 mulService这个大事务所有的事务都回滚

如果checkout Propagation.REQUIRES_NEW  updatePrice  Propagation.REQUIRED
    说明 checkout 新事物  updatePrice和MulService共用一个事务
    两个是独立的 各开一辆车 一个成功 和另一个失败没有关系
*/
    /*
     * multi(){
     *       //REQUIRED
     *       A(){
     *           //REQUIRES_NEW
     *           B(){
     *           }
     *           //REQUIRED
     *           C(){
     *
     *           }
     *       }
     *       //REQUIRES_NEW
     *       D(){
     *           //REQUIRED
     *           E(){
     *               //REQUIRES_NEW
     *               F(){
     *                  10/0
     *               }
     *           }
     *           //REQUIRES_NEW
     *           G(){
     *           }
     *       }
     *   int 10/0;
     *
     * }
     * 大事务和A事务共用一个事务 A里面的B事务单独一个 B能成功  C和A一个事物==>不能成功
     *D事务和大事务不是一个事物 D能成功 E和D同一个事务 虽然F和D不是一个事物但是 F能成功  G和大事务不是一个事务
     *
     * F崩了 但是F没有try/catch 即使F是REQUIRES_NEW整个异常一层一层向外映射 E也崩了 代码不会向下执行 G也不执行
     * A崩了（和大事务同一事务） c崩了（和A同一个事务） B已经执行了并且REQUIRES_NEW一定不会蹦 ☆☆☆☆☆☆☆
     *
     *
     * 子事务如果REQUIRE的话 所有的配置eg： timeout等都是取决于大事务 REQUIRES_NEW可以取决于自己☆☆☆☆☆☆☆☆☆☆☆☆☆☆
     *
     *
     * REQUIRE将之前事务用的connection传递给这个方法使用☆☆☆☆☆☆☆
     * REQUIRES_NEW 直接用新的connection☆☆☆☆☆☆☆
     *
     * */
    /*
    * 传入哪个用户哪本书
    *
    * 事务的细节
    * isolation-Isolation事务的隔离级别
    * propagation-Propagation传播行为
    *   传播行为 = 事务的传播 +事务的行为
    *       如果有多个事务进行嵌套运行 子事务是否要和大事务共用一个事务
    * eg: A和B去旅游 2个选项
    *           A和B共用一辆车去旅游
    *           B和A各自开车去旅游
    * require = B有车的话 A就蹭他的车 否则 自己开车 如果有一个事务在运行 当前的方法就在这个事务内运行
    *                                               否则 就开启一个新的事务 在自己的事务内运行
    * require_new = B 和 A 开各自的车  当前的方法必须开启新事物 并在自己的事务中运行
    *                                           如果有事务在运行 应该将它挂起（暂停）
    * supports B有车的话 A就蹭他的车 否则 B和A都没车就不去旅游了    如果有事务在运行 当前的方法就在这个事务内运行 否则它可以不运行在事务中
    * not_support 当前的方法不应该运行在事务中 如果有运行的事务 将他挂起
    * mandatory 当前的方法必须运行在事务内部 如果没有正在运行的事务 就抛异常（和support不同在于抛异常）
    * never 当前的方法不应该运行在事务中 如果有运行的事务 就抛异常（和not_support不同于抛异常）
    * AService{
    *       tx_a(){
    *           //a事务一些方法
    *           tx_b(){
    *               }
    *           tx_c(){
    *               }
    *       }
    * }
    *
    *
    * noRollBackFor-Class[] 哪些事务异常可以不回滚（让默认回滚的异常不回滚）
    * noRollbackForClassName-String[] 写全类名
    * RollbackFor-Class[] 哪些异常事务可以回滚(针对编译时异常 因为默认编译时异常是不回滚的)
    * RollbackForClassName-String[]
    * 异常：
    *       运行时异常：
    *           可以不用处理
    *       编译时异常：
    *           1.try/catch 默认回滚
    *           2.方法上声明throw 默认不回滚
    *
    * 事务的回滚：默认发生运行时异常都回滚 发生编译时异常不回滚
    *
    * readOnly-boolean 设置事务为只读事务
    *       进行事务优化：加快查询速度 不用管事务那一堆操作
    *       不能针对修改增加删除添加该操作
    * timeout-int（秒为单位） 超时：事务超出指定执行时长后自动终止 回滚
    * */
    @Transactional(timeout = 3,noRollbackFor = {ArithmeticException.class},propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,String isbn){
        //减库存
        bookDao.updateStock(isbn);
/*        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //查价格
        int price = bookDao.getPrice(isbn);
        //减余额
        bookDao.updateBalance(username,price);
        //ArithmeticException.class
        //int i = 10/0;
    }
    //readOnly = true加快查询速度 isolation = Isolation.READ_COMMITTED要求Transaction01只能读取Transaction02已提交的修改
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public int getPrice(String isbn){
       return bookDao.getPrice(isbn);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(String isbn,int price){
        bookDao.updatePrice(isbn,price);
    }


    /*
     * 如果是MulService --mulTx（）调用bookeService两个方法
     * bookService --mulTx（）这届调用两个方法
     *
     * MulServiceProxy.mulTx(){
     *   bookService的代理对象.checkout()
     * bookService的代理对象.updatePrice()
     * }
     *
     * 本类方法的嵌套是一个事务
     *BookServiceProxy.mulTx(){
     *   checkout()
     *   updatePrice()
     *   //相当于把checkout()和updatePrice()的代码复制进来 合成一个事物
     * }
     * */
    @Transactional
    public void mulTx(){
        checkout("Tom","ISBN-001");

        updatePrice("ISBN-002",998);
    }
}
