package com.tcg.platformer.entities;

public interface EntityEvent<T extends AbstractEntity> {

    void accept(T entity);

}
