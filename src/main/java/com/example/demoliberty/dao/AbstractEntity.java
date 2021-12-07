package com.example.demoliberty.dao;

public interface AbstractEntity<I> {
    I getId();

    void setId(I id);
}
