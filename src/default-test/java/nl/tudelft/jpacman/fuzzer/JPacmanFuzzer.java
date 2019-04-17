package nl.tudelft.jpacman.fuzzer;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.io.FileWriter; 
import java.io.IOException;
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
    private PrintWriter writer;
    @SuppressWarnings("magicnumber")
    private static int numRuns = 5;

    void setUpLogging() {
        File dir = new File("logs");
        if (!dir.exists())
            dir.mkdirs();
        for(File f: dir.listFiles())
            if(!f.isDirectory())
                f.delete(); 
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
        writer.flush();
        writer.close();
    }

    /**
     * 
     * Basic fuzzer implementation
     * 
     */
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    void fuzzerTest() {
        int fnum = 0;
        setUpLogging();
        

        for(int i = 0; i < numRuns; i++) {
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

                writer = new PrintWriter(new FileWriter(filename, true));

                while (game.isInProgress()) {
                    double rand = Math.random();
                    if(rand <= 0.25)
                        chosen = Direction.NORTH;
                    else if(rand > 0.25 && rand <= 0.5)
                        chosen = Direction.EAST;
                    else if(rand > 0.5 && rand <= 0.75)
                        chosen = Direction.SOUTH;
                    else
                        chosen = Direction.WEST;
                    log(game, player, chosen, writer);
                    game.getLevel().move(player, chosen);
                }

                log(game, player, chosen, writer);
                game.stop();
                tearDown();
            } catch(IOException e) {}
        }
    }


    private static void log(Game game, Player player, Direction chosen, PrintWriter writer) { 
        writer.printf("\n%b %s %s %d %d", player.isAlive(), player.getDirection(), chosen, game.getLevel().remainingPellets(), player.getScore());
    }
}
