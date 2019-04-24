package nl.tudelft.jpacman.fuzzer;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * A basic fuzzer trying out random moves and writing logs in output files. Number of runs is a
 * tunable parameter. Each run initiates the game and is played until PacMan dies.
 *
 * @author Azqa Nadeem
 */
@Disabled
class JPacmanFuzzer {
    private static final int RUNS = 5;
    private static final String LOG_HEADER =
        "isAlive, hasCollided, currentDirection, nextDirection, remainingPellets, score";
    private static final String BASE_LOG_DIRECTORY = "behavioral-analysis/logs";

    private static File logDirectory;

    private Launcher launcher;

    /**
     * Sets and cleans up directory for storing logs.
     *
     * @throws IOException when file/folder can't be created.
     */
    @BeforeAll
    static void setUpLogging() throws IOException {
        logDirectory = new File(BASE_LOG_DIRECTORY, Long.toString(System.currentTimeMillis()));
        if (!logDirectory.exists() && !logDirectory.mkdirs()) {
            throw new IOException("Directory `behavioral-analysis/` could not be created.");
        }
    }

    /**
     * Launch the user interface.
     */
    @BeforeEach
    void setUpGame() {
        launcher = new Launcher();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     *
     * @throws IOException when log writer cannot be closed.
     */
    @AfterEach
    void tearDown() throws IOException {
        launcher.dispose();
    }

    /**
     * Basic fuzzer implementation.
     *
     * @param repetitionInfo repeated test information
     * @throws IOException when the log write created.
     */
    @RepeatedTest(RUNS)
    void fuzzerTest(RepetitionInfo repetitionInfo) throws IOException {
        Game game = launcher.getGame();
        Direction chosen = Direction.EAST;

        String logFileName = "log_" + repetitionInfo.getCurrentRepetition() + ".txt";
        File logFile = new File(logDirectory, logFileName);

        try (BufferedWriter logWriter = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(logFile, true), StandardCharsets.UTF_8))) {

            logWriter.write(LOG_HEADER);

            try {
                game.start();

                while (game.isInProgress()) {
                    chosen = getRandomDirection();

                    log(logWriter, chosen);
                    game.getLevel().move(game.getPlayers().get(0), chosen);
                }
            } catch (RuntimeException e) {
                // Runtime exceptions should not stop the execution of the fuzzer
            } finally {
                log(logWriter, chosen);
                game.stop();
            }
        }
    }

    private Direction getRandomDirection() {
        return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    private void log(BufferedWriter logWriter, Direction chosen) throws IOException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        logWriter.write(
            String.format(
                "%n%b %b %s %s %d %d",
                player.isAlive(),
                (player.getKiller() != null),
                player.getDirection(),
                chosen,
                game.getLevel().remainingPellets(),
                player.getScore()
            )
        );
    }
}
