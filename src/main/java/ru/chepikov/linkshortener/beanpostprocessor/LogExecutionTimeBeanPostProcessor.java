package ru.chepikov.linkshortener.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Profile("dev")
@ConditionalOnProperty(prefix = "link-shortener", name = "enable-log-exec-time", havingValue = "true")
public class LogExecutionTimeBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, ClassMethods> acceptableBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                acceptableBeans.putIfAbsent(beanName, new ClassMethods(bean.getClass(), new ArrayList<>()));
                acceptableBeans.get(beanName).getMethods().add(method);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ClassMethods classMethods = acceptableBeans.get(beanName);

        if (classMethods == null) {
            return bean;
        }

        Class<?> clazz = classMethods.getClazz();
        List<Method> annotatedMethods = classMethods.getMethods();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
            boolean isAcceptable = annotatedMethods.stream().anyMatch(annotatedMethod -> methodEquals(annotatedMethod, method));

            if (isAcceptable) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                try {
                    return method.invoke(bean, args);
                } finally {
                    stopWatch.stop();
                    log.info("Время выполнения '{}': {} мс",method.getName(), stopWatch.getTotalTimeMillis());
                }
            }
            return method.invoke(bean, args);
        });
    }

    private boolean methodEquals(Method method1, Method method2) {
        if (method1.getName().equals(method2.getName())) {
            return equalParamTypes(method1.getParameterTypes(), method2.getParameterTypes());
        }
        return false;
    }

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
