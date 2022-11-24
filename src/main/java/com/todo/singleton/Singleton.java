package com.todo.singleton;

import com.todo.config.ApplicationContextConfig;
import com.todo.service.CategoryService;
import com.todo.service.PriorityService;
import com.todo.service.ToDoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Singleton {
    private static Singleton singleton;
    private final ToDoService toDoService;
    private final CategoryService categoryService;
    private final PriorityService priorityService;

    private Singleton() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        this.categoryService = annotationConfigApplicationContext.getBean(CategoryService.class);
        this.priorityService = annotationConfigApplicationContext.getBean(PriorityService.class);
        toDoService = annotationConfigApplicationContext.getBean(ToDoService.class);
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public ToDoService getToDoService() {
        return toDoService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public PriorityService getPriorityService() {
        return priorityService;
    }
}
