package com.todo.repositroy;


import com.todo.entity.Category;

public interface CategoryRepository extends Repository<Category> {
    Category findCategoryByName(String name);
}
