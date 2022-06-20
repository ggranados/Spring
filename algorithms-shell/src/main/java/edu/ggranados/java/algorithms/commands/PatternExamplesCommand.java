package edu.ggranados.java.algorithms.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
class PatternExamplesCommand {

    Logger log = LoggerFactory.getLogger(PatternExamplesCommand.class);

    @ShellMethod(key = "ins.srt.ite", value = "insert-sort-iterative")
    public void insertSortIterative() {

        log.info("Insert Sort Iterative example");
    }

    @ShellMethod(key = "ins.srt.rec", value = "insert-sort-recursive")
    public void insertSortRecursive() {

        log.info("Insert Sort Recursive example");
    }



}
