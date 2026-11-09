package ru.gr05506.application;

public interface Command {
    void execute();
    void undo();
}
