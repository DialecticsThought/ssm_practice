package liu.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {
    /*
    * metadataReader读取到当前正在扫描到的类的信息 类似于反射
    * metadataReaderFactory 获取到其他任何类的信息
    *
    * */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类的注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源（类路径）
        Resource resource = metadataReader.getResource();
        //得到雷鸣
        String className = classMetadata.getClassName();

        System.out.println(className+"<---->");
        //如果类名中有“er”这2个字母 就匹配扫描到
        if(className.contains("er")){
            return true;
        }
        //false是指所有类都没有匹配扫描到
        return false;
    }
}
