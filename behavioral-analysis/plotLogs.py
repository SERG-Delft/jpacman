import sys, re
import matplotlib.pyplot as plt

filepath = sys.argv[1]
status, killedbyghost, currdirection, nextDirection, pellets, score = list(), list(), list(), list(), list(), list()
moves = list()

for line in open(filepath).readlines()[1:]:
    #print line
    groups = re.search("(.*) (.*) (.*) (.*) (.*) (.*)", line) # status, killedbyghost, currdirection, nextDirection, pellets, score

    status.append(groups.group(1))
    killedbyghost.append(groups.group(2))
    currdirection.append(groups.group(3))
    nextDirection.append(groups.group(4))
    pellets.append(178-int(groups.group(5)))
    score.append(int(groups.group(6)))

    d = groups.group(4)
    if len(moves) == 0:
        moves.append(d)
    elif moves[-1] != d:
        moves.append(d)

#print moves

plt.subplot(511)
plt.plot(score, 'r')
plt.plot(score, 'r.')
plt.ylabel("Score")
plt.grid()
plt.subplot(512)
plt.plot(pellets, 'b')
plt.plot(pellets, 'b.')
plt.ylabel("Pellets")
plt.grid()
plt.subplot(513)
plt.plot(status, 'g')
plt.ylabel("Alive?")
plt.grid()
plt.subplot(514)
plt.plot(killedbyghost, 'g')
plt.ylabel("KilledByGhost?")
plt.grid()
plt.subplot(515)
plt.plot(nextDirection, 'k')
plt.plot(nextDirection, 'k.')
plt.ylabel("Direction")
plt.grid()

plt.show()
