package liu.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
    //使用@Value赋值代替property标签的value属性：
    // 基本数值
    // spring expression @{}
    // ${} 取出配置文件中的值（运行环境变量的值 因为这些变量都存在environment变量里面）
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private int age;
    @Value("${person.nickname}")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
