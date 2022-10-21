package dev.pizzawunsch.tasks;

import dev.pizzawunsch.UHCenario;
import org.bukkit.Bukkit;

/**
 * This class handles the tasks of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 02.10.2022
 */
public abstract class Task {

    // instance variables.
    private final boolean sync;
    private int task;
    private final int start;
    private final int repeat;
    private boolean state;

    /**
     * Constructor of the task.
     * @param sync if the task should run synchronously
     * @param start when the task should start.
     * @param repeat the time when it should repeat.
     */
    public Task(boolean sync, int start, int repeat) {
        this.sync = sync;
        this.start = start;
        this.repeat = repeat;
    }

    /**
     * The code that should be executed.
     */
    public abstract void execute();

    /**
     * This method runs the task.
     */
    @SuppressWarnings("deprecation")
    public Task run() {
        this.state = true;
        if (this.sync)
            this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(UHCenario.getInstance(), Task.this::execute, this.start, this.repeat);
        else
            this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(UHCenario.getInstance(), Task.this::execute, this.start, this.repeat);
        return this;
    }

    /**
     * This method ends the task.
     */
    public void end() {
        this.state = false;
        Bukkit.getScheduler().cancelTask(this.task);
    }

    /**
     * Checks if the task is currently running.
     * @return if the task is currently running.
     */
    public boolean isRunning() {
        return this.state;
    }
}