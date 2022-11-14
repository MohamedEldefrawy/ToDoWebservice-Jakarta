package com.todo.repositroy;

import com.todo.entity.Priority;

public interface PriorityRepository extends Repository<Priority> {
    Priority findPriorityByName(String name);
}
