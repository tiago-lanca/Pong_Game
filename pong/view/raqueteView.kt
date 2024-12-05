package pong.view

import pong.domain.Raquete
import pt.isel.canvas.Canvas
import pt.isel.canvas.RED

fun drawRaqueteDir(arena: Canvas, raquete: Raquete){
       // raquete direita
    arena.drawRect(x = raquete.center.x, y = raquete.center.y,
                    width = raquete.width,
                    height = raquete.height,
                    color = raquete.color
    )
}

fun drawRaqueteEsq(arena: Canvas, raquete: Raquete) {
    // raquete esquerda
    arena.drawRect(x = raquete.center.x, y = raquete.center.y,
        width = raquete.width,
        height = raquete.height,
        color = raquete.color
    )
}