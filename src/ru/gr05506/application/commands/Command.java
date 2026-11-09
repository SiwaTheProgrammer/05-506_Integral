package ru.gr05506.application.commands;

public interface Command {
    void execute();
    void undo();
}
