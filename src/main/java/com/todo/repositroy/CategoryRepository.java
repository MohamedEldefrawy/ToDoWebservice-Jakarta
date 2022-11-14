package com.todo.repositroy;

import com.swe.todoconsoleapp.entity.Category;

public interface CategoryRepository  extends Repository<Category>{
    Category findCategoryByName(String name);
}
