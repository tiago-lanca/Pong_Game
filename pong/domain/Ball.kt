package pong.domain
import pong.view.Arena
import pt.isel.canvas.*
import pong.view.arenaHeight
import pong.view.arenaWidth
import kotlin.random.Random.Default.nextInt

const val ballRadius = 10


data class Location (var x: Int, var y: Int)
data class Velocity (var dx: Int, var dy: Int)
//data class Acceleration (var dvx: Int, var dvy: Int)

data class Ball(var center: Location, var radius: Int, var velocity: Velocity, var color: Int)

fun gamePlaying(canvas: Canvas, arena: Arena): Arena{
    arena.ball = moveBall(canvas, arena)
    return Arena(
        arena.ball,
        arena.raqueteDir,
        arena.raqueteEsq,
        scoreEsq =
            if(isRaqueteDirLoss(arena)) arena.scoreEsq + 1 else arena.scoreEsq,
        scoreDir =
            if(isRaqueteEsqLoss(arena)) arena.scoreDir + 1 else arena.scoreDir,
    )
}

fun moveBall(canvas: Canvas, arena: Arena): Ball {

    val newBall = Ball(
        Location(
            x = arena.ball.center.x + arena.ball.velocity.dx,
            y = arena.ball.center.y + arena.ball.velocity.dy
        ),
        radius = ballRadius,
        velocity = Velocity(arena.ball.velocity.dx, arena.ball.velocity.dy),
        //acceleration = Acceleration(ball.acceleration.dvx, ball.acceleration.dvy),
        color = arena.ball.color
    )
    return when{
        touchRaquete(newBall, arena.raqueteDir, arena.raqueteEsq) ->
            deflectBall(canvas, newBall, arena.raqueteEsq, arena.raqueteDir)

        !isInYLimits(canvas, newBall) ->
            deflectBall(canvas, newBall, arena.raqueteEsq, arena.raqueteDir)

        else -> newBall
    }
}

fun deflectBall(canvas: Canvas, ball: Ball, raqueteEsq: Raquete, raqueteDir: Raquete): Ball{
    return Ball(
    center =
        if(!isInYLimits(canvas, ball) && ball.velocity.dy > 0)
            Location(ball.center.x , arenaHeight - ballRadius)
        else if (!isInYLimits(canvas, ball) && ball.velocity.dy < 0)
            Location(ball.center.x, ballRadius)
        else
        Location(x = ball.center.x, y = ball.center.y),
    ball.radius,
    velocity =
        if(touchRaquete(ball, raqueteDir, raqueteEsq))
            Velocity(dx = ball.velocity.dx * -1, dy = touchRaqueteAdjustBall(ball, raqueteEsq, raqueteDir))
        else if (!isInYLimits(canvas, ball))
            Velocity(dx = ball.velocity.dx, dy = ball.velocity.dy * -1)
        else
            Velocity(dx = ball.velocity.dx, dy = ball.velocity.dy),
    ball.color)
}

fun touchRaqueteAdjustBall (ball: Ball, raqueteEsq: Raquete, raqueteDir: Raquete): Int {
    val ballMovingDown = ball.velocity.dy > 0

    return if (touchRaqueteTop(ball, raqueteEsq))
             if (ballMovingDown) ball.velocity.dy * -2 else ball.velocity.dy * 2
        else if (touchRaqueteBottom(ball, raqueteEsq))
             if (ballMovingDown) ball.velocity.dy * 2 else ball.velocity.dy * -2

        else if (touchRaqueteTop(ball, raqueteDir))
            if (ballMovingDown) ball.velocity.dy * -2 else ball.velocity.dy * 2
        else if (touchRaqueteBottom(ball, raqueteDir))
            if (ballMovingDown) ball.velocity.dy * 2 else ball.velocity.dy * -2
        else ball.velocity.dy


}

fun restartGame(ball: Ball, arena: Canvas): Ball {

    val newBall =  Ball(
        Location(
            x = arenaWidth / 2 ,
            y = arenaHeight / 2
        ),
        radius = ballRadius,
        velocity = Velocity(0, 0),
        //acceleration = Acceleration(ball.acceleration.dvx, ball.acceleration.dvy),
        color = ball.color
    )

    return newBall
}

fun isInLimits(arena: Canvas, ball: Ball) =
    isInXLimits(arena, ball) && isInYLimits(arena, ball)

fun isInXLimits(arena: Canvas, ball: Ball) =
    (ball.center.x - ballRadius > 0 && ball.center.x + ballRadius < arenaWidth)


fun isInYLimits(arena: Canvas, ball: Ball) =
     (ball.center.y + ballRadius < arenaHeight && ball.center.y - ballRadius > 0)


fun isBallMoving (ball: Ball) =
    ball.velocity.dx != 0 || ball.velocity.dy != 0

fun isRaqueteDirLoss (arena: Arena) =
     arena.ball.center.x + ballRadius >= arenaWidth

fun isRaqueteEsqLoss (arena: Arena) =
    arena.ball.center.x - ballRadius <= 0

fun startGame (arena: Arena): Ball {
    return if (isBallMoving(arena.ball)) arena.ball
    else arena.ball.copy(
        velocity = Velocity(dx = nextInt(10, 15), dy = nextInt( -5, 5)))
}



