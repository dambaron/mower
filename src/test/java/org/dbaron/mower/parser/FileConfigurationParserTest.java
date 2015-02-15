package org.dbaron.mower.parser;

import com.google.common.collect.ImmutableSet;
import org.dbaron.mower.service.MoveProviderServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

public class FileConfigurationParserTest {

    private static final ImmutableSet<String> ALLOWED_ORIENTATIONS = ImmutableSet.of("N", "S", "W", "E");
    private static final ImmutableSet<String> ALLOWED_MOVES = ImmutableSet.of("G", "D", "A");

    private final ClassLoader classLoader = this.getClass().getClassLoader();

    private final FileConfigurationParser fileConfigurationParser =
            new FileConfigurationParser(ALLOWED_ORIENTATIONS, ALLOWED_MOVES);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        fileConfigurationParser.setMoveProviderService(new MoveProviderServiceImpl());
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForNullFile() {

        exception.expect(NullPointerException.class);
        exception.expectMessage("file is required");

        fileConfigurationParser.parseConfiguration((File) null);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForEmptyFile() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/emptyConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForOneLineFile() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/oneLineConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForTwoLinesFile() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Configuration should contain 3 elements at least");

        String path = classLoader.getResource("parser/twoLinesConfigurationFile.txt").getPath();
        File file = new File(path);

        fileConfigurationParser.parseConfiguration(file);
    }

    @Test
    public void testParseConfigurationThrowsIllegalArgumentExceptionForEvenLinesFile() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Configuration should contain an odd number of elements");

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