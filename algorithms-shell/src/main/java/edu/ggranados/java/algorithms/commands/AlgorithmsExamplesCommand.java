package edu.ggranados.java.algorithms.commands;

import edu.ggranados.java.algorithms.sort.SortType;
import edu.ggranados.java.algorithms.handlers.factories.TypeHandlerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
class SortAlgorithmExamplesCommand {

    private static final Logger logger = LogManager.getLogger(SortAlgorithmExamplesCommand.class);

    @Qualifier("sortTypeHandlerFactory")
    private final TypeHandlerFactory handlerFactory;

    @Autowired
    public SortAlgorithmExamplesCommand(TypeHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @ShellMethod(key = "ins_sort", value = "insert Sort Algorithm")
    public void insertSortIterative(
            @ShellOption String type){

        try {
            handlerFactory
                    .withKey(SortType.INSERT)
                    .withType(type)
                    .build()
                    .execute();
        }
        catch (IllegalArgumentException e) {
            logger.error("Options : {} is not found - Expected: {}",
                    () -> type,
                    SortType::showOptions);
        }

    }



}
