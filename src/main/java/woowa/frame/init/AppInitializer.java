package woowa.frame.init;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * 커스텀 설정을 실행하는 첫 진입점입니다.
 * 패키지에 존재하는 초기화 클래스를 찾아서 실행합니다.
 */
@WebListener
public class AppInitializer implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("start init");
        var classes = ClassUtils.scan("woowa")
                .toArray(new Class[0]);

        // run init logic
        for (Class clazz : classes) {
            if (Arrays.asList(clazz.getInterfaces()).contains(Initializer.class)) {
                try {
                    Constructor<?> constructor = clazz.getConstructor();
                    Initializer initializer = (Initializer) constructor.newInstance();
                    initializer.init(classes);
                    logger.info("{} init", constructor.getName());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("application down....");
    }
}
