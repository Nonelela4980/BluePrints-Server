package com.example.capstone;

import java.io.Serializable;
public abstract class Message<C> implements Serializable {
    private static final long serialVersionUID = 999L;
    public void applyLogic(C context){};
}
