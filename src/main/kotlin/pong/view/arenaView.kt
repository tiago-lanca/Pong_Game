package pong.view

import pong.domain.*
import pt.isel.canvas.*
import pt.isel.canvas.WHITE

const val arenaWidth = 600
const val arenaHeight = 500

data class Arena (var ball: Ball, var raqueteDir: Raquete, var raqueteEsq: Raquete, var scoreEsq: Int, var scoreDir: Int)

fun initializeArena(): Arena {
    var ball = Ball(
        center = Location(arenaWidth / 2, arenaHeight/2),
        radius = ballRadius,
        velocity = Velocity(dx = 0,dy = 0),
        //acceleration = Acceleration(dvx = 1, dvy = 1),
        color = GREEN)

    var raqueteDir = Raquete(
        center = Location(x = arenaWidth - 20, y = arenaHeight / 2),
        width = 5,
        height = 60,
        color = RED
    )
    var raqueteEsq = Raquete(
        center = Location(x = 20, y = arenaHeight / 2),
        width = 5,
        height = 60,
        color = RED
    )
    var scoreEsq = 0
    var scoreDir = 0

    return Arena(ball, raqueteDir, raqueteEsq, scoreEsq, scoreDir)
}

fun drawArena (arena: Arena, canvas: Canvas) {
    canvas.erase()
    drawLines(canvas)
    drawBall(canvas, arena.ball)
    drawRaqueteDir(canvas, arena.raqueteDir)
    drawRaqueteEsq(canvas, arena.raqueteEsq)
    drawScore(canvas, arena)
}

fun drawBall(canvas: Canvas, ball: Ball) {
    canvas.drawCircle(
        xCenter = ball.center.x,
        yCenter = ball.center.y,
        radius = ball.radius,
        color = ball.color
    )
}

fun drawScore (canvas: Canvas, arena: Arena){
    // Score Esquerda
    canvas.drawText(
        x = arenaWidth / 2 - 150, //
        y = 60,
        txt = arena.scoreEsq.toString(),
        color = WHITE,
        fontSize = 50
    )

    // Score Direita
    canvas.drawText(
        x = arenaWidth / 2 + 100, //
        y = 60,
        txt = arena.scoreDir.toString(),
        color = WHITE,
        fontSize = 50
    )
}

fun drawLines (arena: Canvas){
    arena.drawLine(
        xFrom = arenaWidth / 2,
        yFrom = 0,
        xTo = arenaWidth / 2,
        yTo = arenaHeight,
        color = WHITE,
        thickness = 1
    )

    arena.drawCircle(
        xCenter = arenaWidth / 2,
        yCenter = arenaHeight / 2,
        radius = 40,
        color = WHITE,
        thickness = 1
    )
}

