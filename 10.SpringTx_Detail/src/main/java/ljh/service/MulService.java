package ljh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {
    @Autowired
    private BookService bookService;
    @Transactional
    public void mulService(){

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

        //updatePrice出问题的话 checkout要回滚么 这是可以设置的通过传播行为（是不是和之前的大事务共享一个事务 也就是使用同一sql连接）
        bookService.checkout("Tom","ISBN-001");

        bookService.updatePrice("ISBN-002",998);

        int i=10/0;
    }
}
