package com.example.trackingcore.application.usecase;

public abstract class UseCase<I, O> {
    public abstract O execute(I input);
}
