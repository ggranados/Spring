package edu.ggranados.java.algorithms.handlers;

import edu.ggranados.java.algorithms.sort.Sorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static edu.ggranados.java.algorithms.utils.IntegerUtils.getIntegerList;

@Lazy
@Component
@Qualifier("insertSortRecursive")
public class InsertSortRecursiveHandler implements TypeHandler {
    private static final Logger logger = LogManager.getLogger(InsertSortRecursiveHandler.class);

    private Sorter<Integer> sorter;

    @Autowired
    public InsertSortRecursiveHandler(
            @Qualifier("insertSorterRecursive") Sorter<Integer> sorter) {
        this.sorter = sorter;
    }

    @Override
    public void handle() {

        logger.info("Executing Insert Sort Recursive Algorithm...");

        ArrayList<Integer> elements = new ArrayList<>(getIntegerList());
        logger.info(elements);

        var sortedElements = sorter.sort(elements);
        logger.info(sortedElements);

    }
}
