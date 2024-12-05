import pong.domain.*
import pong.view.*
import pt.isel.canvas.*
import java.awt.event.KeyEvent

fun main() {

    onStart {

        // Desenha a arena no ecrã
        val canvas = Canvas(width = arenaWidth, height = arenaHeight, background = BLACK)

        var arena = initializeArena()

        canvas.onKeyPressed { key ->
            arena.raqueteDir = when (key.code) {
                KeyEvent.VK_UP -> keepRaqueteInLimits(canvas, arena.raqueteDir, raqueteDirection.UP)
                KeyEvent.VK_DOWN -> keepRaqueteInLimits(canvas, arena.raqueteDir, raqueteDirection.DOWN)

                else -> arena.raqueteDir
            }

            arena.raqueteEsq = when (key.code) {
                KeyEvent.VK_W -> keepRaqueteInLimits(canvas, arena.raqueteEsq, raqueteDirection.UP)

                KeyEvent.VK_S -> keepRaqueteInLimits(canvas, arena.raqueteEsq, raqueteDirection.DOWN)

                else -> arena.raqueteEsq
            }

            arena.ball = when (key.code) {
                KeyEvent.VK_F -> startGame(arena)

                else -> arena.ball
            }

            arena = when (key.code) {
                KeyEvent.VK_R -> resetScores(arena)
                else -> arena
            }
        }

        canvas.onTimeProgress(period = 25) {

            if (isInXLimits(canvas, arena.ball)) {
                arena = gamePlaying(canvas, arena)
                drawArena(arena, canvas)
            }
            else {
                arena.ball = restartGame(arena.ball, canvas)
                arena.raqueteDir = resetRaqueteDir(canvas, arena.raqueteDir)
                arena.raqueteEsq = resetRaqueteEsq(canvas, arena.raqueteEsq)
            }
        }
    }


    onFinish {
        println("Fim")
    }
}