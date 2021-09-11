package ljh.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Person {
    /*
    * 基本类型直接使用
    *   <property name="lastName" value="bbb"></property>
        <property name="age" value="11"></property>
        <property name="email" value="admin@qq.com"></property>
        <property name="gender" value="man"></property>
        自动地类型转换 因为xml里面写的都是写纯文本
    *
    */
    private Integer age;
    private String email;
    private String gender;
    private String lastName = "小明";
    private Car car;
    private List<Book> books;
    private Map<String,Object> maps;
    private Properties properties;
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Person() {
        System.out.println("调用了无参构造函数");
    }

    public Person(Car car) {
        this.car = car;
       System.out.println(" 可以为car复制的有参构造器");
    }

    public Person(Integer age, String gender, String lastName) {
        this.age = age;
        this.gender = gender;
        this.lastName = lastName;
    }


    public Person(Integer age, String email, String gender, String lastName) {
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.lastName = lastName;
        System.out.println("调用了有参构造函数");
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", lastName='" + lastName + '\'' +
                ", car=" + car +
                ", books=" + books +
                ", maps=" + maps +
                ", properties=" + properties +
                ", salary=" + salary +
                '}';
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("调用了setAge");
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        System.out.println("调用了setEmail");
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        System.out.println("调用了setGender");
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        System.out.println("调用了setLastName");
        this.lastName = lastName;
    }

}
