package ru.gr05506.application.commands;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {
    private final Deque<Command> history = new ArrayDeque<>();

    public void execute(Command command){
        if (command.canExecute()) {
            command.execute();
            history.push(command);
        }
    }

    public boolean undo(){
        if (history.isEmpty()) return false;
        history.pop().undo();
        return true;
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}
