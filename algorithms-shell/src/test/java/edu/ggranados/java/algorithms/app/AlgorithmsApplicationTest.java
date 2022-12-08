package edu.ggranados.java.algorithms.app;


import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.shell.result.DefaultResultHandler;

import static org.hamcrest.Matchers.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class AlgorithmsApplicationTest {

    @Autowired
    private Shell shell;

    @Autowired
    private DefaultResultHandler resultHandler;

    @Test
    void testShellIsRespondingAtHelpCommand(){
        //given

        //when
        Object help = shell.evaluate(() -> "help");

        //then
        MatcherAssert.assertThat(help, is(not(nullValue())));

        resultHandler.handleResult(help);
        System.out.println(help);
    }

}