package edu.ggranados.java.algorithms.handlers;

import edu.ggranados.java.algorithms.sort.InsertSortIterative;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static edu.ggranados.java.algorithms.utils.IntegerUtils.getIntegerList;

public class InsertSortIterativeHandler implements TypeHandler{

    private static final Logger logger = LogManager.getLogger(InsertSortIterativeHandler.class);

    @Override
    public void execute() {
        InsertSortIterative insertSort = new InsertSortIterative();

        ArrayList<Integer> elements = new ArrayList<>(getIntegerList());
        logger.info(elements);

        var sortedElements = insertSort.sort(elements);
        logger.info(sortedElements);

    }

}
