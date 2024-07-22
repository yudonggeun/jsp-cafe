package woowa.frame.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class BeanContainer {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    /**
     * 생성하고자하는 빈의 클래스를 등록합니다
     *
     * @param classes 등록하고자하는 빈의 클래스
     */
    public void registerBean(Class<?>...classes) {
        for (Class<?> clazz : classes) {
            beans.put(clazz, null);
        }
    }

    /**
     * 초기화한 빈을 등록합니다.
     *
     * @param bean 등록하고자하는 빈 객체
     */
    public void registerBean(Object bean) {
        beans.put(bean.getClass(), bean);
    }

    /**
     * 현재 등록된 빈을 생성합니다.
     */
    public void createBeans() {
        for (Class<?> clazz : beans.keySet()) {
            getBean(clazz);
        }
    }

    /**
     * 빈 컨테이너에서 등록된 빈을 조회합니다. 만약 빈이 없다면 예외가 발생합니다.
     * 하지만 아직 초기화 작업이 되지 않은 빈이라면 예외를 발생시키지 않고 새로 빈을 생성하여 등록해줍니다.
     *
     * @param clazz 컨테이너에서 조회할 빈의 클래스 타입
     * @param <T>   빈의 클래스 타입
     * @return 빈 객체
     */
    public <T> T getBean(Class<T> clazz) {
        T bean = (T) beans.get(clazz);

        if (bean != null) {
            return bean;
        }

        if (!beans.containsKey(clazz)) {
            throw new RuntimeException("빈으로 등록되진 않은 클래스입니다.");
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 1) {

            Constructor<T> constructor = (Constructor<T>) constructors[0];
            Parameter[] args = constructor.getParameters();

            List<Object> parameters = new ArrayList<>();
            for (Parameter arg : args) {
                Object object = getBean(arg.getType());
                parameters.add(object);
            }

            try {
                bean = constructor.newInstance(parameters.toArray());
                beans.put(bean.getClass(), bean);
                return bean;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("빈으로 등록할 클래스의 생성자를 호출할 수 없습니다.");
            }
        } else {
            throw new RuntimeException("빈으로 등록할 클래스의 생성자는 하나이어야 합니다.");
        }
    }
}
