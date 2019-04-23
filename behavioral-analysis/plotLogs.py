import sys, re
import matplotlib.pyplot as plt

# read the log file path
filepath = sys.argv[1]

# declare storage variables
status, killedbyghost, currdirection, nextDirection, pellets, score = list(), list(), list(), list(), list(), list()
moves = list()

# read log file, line by line
for line in open(filepath).readlines()[1:]:
    #print line

    # Using regular expressions to match log lines
    groups = re.search("(.*) (.*) (.*) (.*) (.*) (.*)", line) # status, killedbyghost, currdirection, nextDirection, pellets, score

    # Collect values in lists to be plotted 
    status.append(groups.group(1)) # status (player.isAlive())
    killedbyghost.append(groups.group(2)) # (player.getKiller())
    currdirection.append(groups.group(3)) # Current direction of pacman
    nextDirection.append(groups.group(4)) # Next direction that pacman chooses to take. 
    #                                       This comes from the randomly chosen direction in fuzzer
    pellets.append(178-int(groups.group(5))) # Subtracting by 178 (total pellets) to get pellets consumed. 
    score.append(int(groups.group(6))) # (player.getScore())

    # collect the sequence of unique moves taken. [N, N, W, W, W, S] becomes [N, W, S]
    d = groups.group(4)
    if len(moves) == 0:
        moves.append(d)
    elif moves[-1] != d:
        moves.append(d)



# Plotting the variables.
# Creating 5 subplots on one main plot
plt.subplot(511) # Subplot 1
plt.plot(score, 'r') # Score is plotted in red
plt.plot(score, 'r.') # This replots score but with . at each measurement
plt.ylabel("Score") # Set the label of plot
plt.grid() # Show grid for easier visualization
#
plt.subplot(512) # Subplot 2
plt.plot(pellets, 'b')
plt.plot(pellets, 'b.')
plt.ylabel("Pellets")
plt.grid()
#
plt.subplot(513) # Subplot 3
plt.plot(status, 'g')
plt.ylabel("Alive?")
plt.grid()
#
plt.subplot(514) # Subplot 4
plt.plot(killedbyghost, 'y')
plt.ylabel("KilledByGhost?")
plt.grid()
#
plt.subplot(515) # Subplot 5
plt.plot(nextDirection, 'k')
plt.plot(nextDirection, 'k.')
plt.ylabel("Direction")
plt.grid()

plt.show() # show the final plot on screen. You can use plt.savefig() to save plot on disk
