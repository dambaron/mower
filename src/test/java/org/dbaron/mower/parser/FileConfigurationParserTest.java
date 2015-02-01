package org.dbaron.mower.parser;

import com.google.common.collect.ImmutableSet;
import org.dbaron.mower.service.MoveProviderServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/spring/mower-spring-context-test.xml"})
public class FileConfigurationParserTest {

    private static final ImmutableSet<String> ALLOWED_ORIENTATIONS = ImmutableSet.of("N", "S", "W", "E");
    private static final ImmutableSet<String> ALLOWED_MOVES = ImmutableSet.of("G", "D", "A");

    private ClassLoader classLoader = this.getClass().getClassLoader();

    private FileConfigurationParser fileConfigurationParser =
            new FileConfigurationParser(ALLOWED_ORIENTATIONS, ALLOWED_MOVES);

    @Before
    public void setUp() {
        fileConfigurationParser.setMoveProviderService(new MoveProviderServiceImpl());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = NullPointerException.class)
    public void testParseConfigurationThrowsIllegalArgumentExceptionForNullFile() {
        File file = null;
        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForEmptyFile() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/emptyConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForOneLineFile() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/oneLineConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForTwoLinesFile() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/twoLinesConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForEvenLinesFile() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Configuration should contain an odd number of elements");

        String path = classLoader.getResource("parser/evenLinesConfigurationFile.txt").getPath();
        File file = new File(path);
        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationFromFile() {
        String path = classLoader.getResource("parser/validConfigurationFile.txt").getPath();
        File file = new File(path);
        fileConfigurationParser.parseConfiguration(file);
    }
}