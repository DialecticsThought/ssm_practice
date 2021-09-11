package ljh.bean;

public class Book {
    private String bookName;
    private String author;

    public Book() {
        System.out.println("调用book无参构造器");
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public void myDestory(){
        System.out.println("这是销毁的方法");
    }

    public void myInitalize(){
        System.out.println("这是创建的方法");
    }
}
