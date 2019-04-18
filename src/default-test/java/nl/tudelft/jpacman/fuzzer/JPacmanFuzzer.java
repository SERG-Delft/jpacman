package nl.tudelft.jpacman.fuzzer;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream; 
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.File;


/**
 * A basic fuzzer trying out random moves
 * and writing logs in output files.  
 * Number of runs is a tunable parameter.
 * Each run initiates the game and is played 
 * until pacman dies.
 *
 * @author Azqa Nadeem, April 2019.
 */
public class JPacmanFuzzer {
    private Launcher launcher;
    private BufferedWriter writer;
    @SuppressWarnings("magicnumber")
    private static int numRuns = 5;

    /**
     * Sets and cleans up directory for storing logs.
     * @throws IOException when file/folder can't be created.
     */
    void setUpLogging() throws IOException {
        File dir = new File("logs");
        if (dir != null) {
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException("Problem when creating logs/");
                }
            }
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f: files) { 
                    if (!f.isDirectory()) {
                        if (!f.delete()) {
                            throw new IOException("Problem when deleting logs");
                        }
                    } 
                }
            } else {
                throw new IOException("Problem when deleting logs");
            }
        }
    }
    /**
     * Launch the user interface.
     */
    void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) { 
        }
        
    }

    /**
     * 
     * Basic fuzzer implementation.
     * 
     */
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    void fuzzerTest() {
        int fnum = 0;

        try {
            setUpLogging();
        } catch (IOException e) {
        }
        

        for (int i = 0; i < numRuns; i++) {
            fnum++;
            setUpPacman();
            Game game = launcher.getGame();
            Player player = game.getPlayers().get(0);
            String filename = "logs/log" + fnum + ".txt";
            Direction chosen = Direction.EAST; 

 
            try {
                // start cleanly. 
                assertThat(game.isInProgress()).isFalse();
                game.start();

                writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename, true), StandardCharsets.UTF_8));
                writer.write("isAlive, hasCollided, currentDirection, "
                    + "nextDirection, remainingPellets, score");

                while (game.isInProgress()) {
                    double rand = Math.random();
                    if (rand <= 0.25) {
                        chosen = Direction.NORTH;
                    } else if (rand > 0.25 && rand <= 0.5) {
                        chosen = Direction.EAST;
                    } else if (rand > 0.5 && rand <= 0.75) {
                        chosen = Direction.SOUTH;
                    } else {
                        chosen = Direction.WEST;
                    }
                    log(game, player, chosen, writer);
                    game.getLevel().move(player, chosen);
                }
                log(game, player, chosen, writer);
                game.stop();
                tearDown();
            } catch (IOException e) {
            } catch (RuntimeException e) {
                log(game, player, chosen, writer);
                game.stop();
                tearDown(); 

            }
        }
    }

    /**
     * Prints logs.
     */
    private static void log(Game game, Player player, Direction chosen, BufferedWriter writer) { 
        try {
            writer.write(String.format("%n%b %b %s %s %d %d", 
                player.isAlive(), 
                (player.getKiller() == null), 
                player.getDirection(), 
                chosen, 
                game.getLevel().remainingPellets(), 
                player.getScore()));
        } catch (IOException e) { 
        }
    }
}
