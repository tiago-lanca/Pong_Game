package pong.domain

import pong.view.arenaHeight
import pong.view.arenaWidth
import pt.isel.canvas.Canvas
import java.awt.event.KeyEvent

data class Raquete (var center: Location, val width: Int, val height: Int, val color: Int)

const val quarterBall = 60/2

enum class raqueteDirection{
    UP, DOWN
}

fun touchRaqueteTop (ball: Ball, raquete: Raquete): Boolean{
    val raqueteTop = raquete.center.y + raquete.height/2

    return ball.center.y + ballRadius > raquete.center.y && ball.center.y + ballRadius < raqueteTop
}

fun touchRaqueteBottom (ball: Ball, raquete: Raquete): Boolean{
    val raqueteBottom = (raquete.center.y + raquete.height) - raquete.height/2

    return ball.center.y - ballRadius < raquete.center.y + raquete.height && ball.center.y - ballRadius > raqueteBottom
}

fun touchRaqueteDir (ball: Ball, raqueteDir: Raquete): Boolean {

    return if(ball.center.x + ballRadius >= raqueteDir.center.x)
        (ball.center.y + ballRadius > raqueteDir.center.y)
                && (ball.center.y - ballRadius <= raqueteDir.center.y + raqueteDir.height)

    else false
}

fun touchRaqueteEsq (ball: Ball, raqueteEsq: Raquete): Boolean {
    return if (ball.center.x - ballRadius <= raqueteEsq.center.x)
        (ball.center.y + ballRadius > raqueteEsq.center.y)
                && (ball.center.y - ballRadius <= raqueteEsq.center.y + raqueteEsq.height)

    else false
}

fun touchRaquete(ball: Ball, raqueteDir: Raquete, raqueteEsq: Raquete) =
     (touchRaqueteEsq(ball, raqueteEsq) || touchRaqueteDir(ball, raqueteDir))


fun resetRaqueteEsq(arena: Canvas, raqueteEsq: Raquete): Raquete{
    val newRaqueteEsq = Raquete(
        Location(
            x = 15, y = arenaHeight / 2
        ),
        width = raqueteEsq.width,
        height = raqueteEsq.height,
        color = raqueteEsq.color
    )
    return newRaqueteEsq
}

fun resetRaqueteDir(arena: Canvas, raqueteDir: Raquete): Raquete{

    val newRaqueteDir = Raquete(
        Location(
            x = arenaWidth - 15, y = arenaHeight / 2
        ),
        width = raqueteDir.width,
        height = raqueteDir.height,
        color = raqueteDir.color
    )
    return newRaqueteDir
}

fun keepRaqueteInLimits (arena: Canvas, raquete: Raquete, direction: raqueteDirection): Raquete {
    var newRaquete = Raquete(
        center = Location(x = raquete.center.x, raquete.center.y),
        width = raquete.width,
        height = raquete.height,
        color = raquete.color)

    val margin = 40
    val raqueteInTopLimit  = raquete.center.y - margin > 0
    val raqueteInBottomLimit = raquete.center.y + raquete.height + margin < arenaHeight

     return when (direction){
        raqueteDirection.UP ->
            if(raqueteInTopLimit)
             raquete.copy(center = Location(x = raquete.center.x, y = raquete.center.y - margin))
            else newRaquete

        raqueteDirection.DOWN ->
            if(raqueteInBottomLimit)
             raquete.copy(center = Location(raquete.center.x,raquete.center.y + margin))
            else  newRaquete
     }
}

fun isRaqueteInYLimits (arena: Canvas, raquete: Raquete): Boolean {
    val margin = 20

    return (raquete.center.y - margin >= 0 && raquete.center.y + raquete.height + margin < arenaHeight)
}
