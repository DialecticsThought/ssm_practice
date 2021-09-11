package liu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
/*
*
* 一定要在pom.xml <packaging>war</packaging>
*
* */
@ComponentScan(value = "liu",
        excludeFilters =
                {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})}
)
public class RootConifg {
}
