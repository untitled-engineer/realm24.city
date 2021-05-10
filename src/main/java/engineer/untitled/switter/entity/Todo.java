package engineer.untitled.switter.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Todo extends AbstractEntity {

    private boolean done = false;
    @NotBlank
    private String task;

    public Todo() {
    }

    public Todo(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}