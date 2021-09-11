package liu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//自定义返回需要导入的组件
public class MyImportSelector implements ImportSelector {
    //返回值 就是根据全类名把组件导入到容器中
    /*
    * AnnotationMetadata importingClassMetadata 当前标注@Inport注解的类的所有注解信息
    * eg：某个类有@Conditional 那么就会返回该注解的信息
    * */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        //返回不是是null值
        return new String[]{"liu.bean.Blue","liu.bean.Red"};
    }
}
