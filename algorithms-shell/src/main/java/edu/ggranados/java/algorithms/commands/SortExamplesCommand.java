package edu.ggranados.java.algorithms.commands;

import edu.ggranados.java.algorithms.handlers.TypeHandlerStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
class SortExamplesCommand {

    private static final Logger logger = LogManager.getLogger(SortExamplesCommand.class);
    public static final String SORT_ITERATIVE_ALGORITHM = "Insert Sort Iterative Algorithm";
    public static final String SORT_RECURSIVE_ALGORITHM = "Insert Sort Recursive Algorithm";
    public static final String INSOIT = "insoit";
    public static final String INSORE = "insore";

    TypeHandlerStrategy handlerStrategy;

    @Autowired
    public SortExamplesCommand(TypeHandlerStrategy handlerStrategy) {
        this.handlerStrategy = handlerStrategy;
    }

    @ShellMethod(key = INSOIT, value = SORT_ITERATIVE_ALGORITHM)
    public void insertSortIterative(){

        logger.info("invoked command {} : {}...", INSOIT, SORT_ITERATIVE_ALGORITHM);
        handlerStrategy.getInsertSortIterative().handle();

    }

    @ShellMethod(key = INSORE, value = SORT_RECURSIVE_ALGORITHM)
    public void insertSortRecursive(){

        logger.info("invoked command {} : {}...", INSOIT, SORT_RECURSIVE_ALGORITHM);
        handlerStrategy.getInsertSortRecursive().handle();

    }



}
