package pong.domain

import pong.view.Arena
import pong.view.drawArena
import pt.isel.canvas.Canvas


fun resetScores(arena: Arena): Arena {
    return Arena(
        arena.ball,
        arena.raqueteDir,
        arena.raqueteEsq,
        scoreEsq = 0,
        scoreDir = 0
    )
}