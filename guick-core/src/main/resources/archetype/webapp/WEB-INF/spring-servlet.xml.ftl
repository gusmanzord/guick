<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <mvc:annotation-driven conversion-service="conversionService">
        <mvc:argument-resolvers>
            <bean class="org.springframework.data.web.PageableArgumentResolver"/>
        </mvc:argument-resolvers>
    </mvc:annotation-driven>

    <bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <list>
            </list>
        </property>
        <property name="formatters">
            <list>
                <bean class="org.springframework.format.datetime.DateFormatter"/>
            </list>
        </property>
    </bean>

    <bean class="org.springframework.data.repository.support.DomainClassConverter">
        <constructor-arg ref="conversionService" />
    </bean>

    <context:component-scan base-package="${project.group}.${project.name}.presentation"/>

    <bean class="org.springframework.data.repository.support.DomainClassConverter">
        <constructor-arg ref="conversionService" />
    </bean>

    <bean name="/home.do" class="org.springframework.web.servlet.mvc.ParameterizableViewController">
        <property name="viewName" value="home"/>
    </bean>

</beans>