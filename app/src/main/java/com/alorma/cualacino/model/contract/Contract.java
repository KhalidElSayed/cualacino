package com.alorma.cualacino.model.contract;

/**
 * Created by Bernat on 11/02/14.
 */
public interface Contract {
    String create();
    String alter(int oldVersion, int newVersion);
}
