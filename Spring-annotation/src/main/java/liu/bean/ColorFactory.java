package liu.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactory implements FactoryBean<Color> {
    /*
    * 返回一个color对象 并添加到容器中
    * */
    @Override
    public Color getObject() throws Exception {
        System.out.println("返回一个Color对象");
        return new Color();
    }
    //返回的对象的类型
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }
    /*
    * true的话 bean是单例 在容器中只保存一份
    *  false  bean是多例  每次调用getObject()返回一个bean对象
    * */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
