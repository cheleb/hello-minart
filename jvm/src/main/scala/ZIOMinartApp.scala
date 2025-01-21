package dev.cheleb.hellominart

import zio.*
import zio.stream.*
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.backend.defaults.given

object ZIOMinartApp extends ZIOAppDefault {

  private val canvasSettings =
    Canvas.Settings(width = 128, height = 128, scale = Some(4))

  def draw(canvas: Canvas): ZIO[Any, Nothing, Unit] =
    ZStream
      .range(0, canvas.width)
      .foreach { x =>
        ZStream.range(0, canvas.height).foreach { y =>
          val color =
            Color(
              (255 * x.toDouble / canvas.width).toInt,
              (255 * y.toDouble / canvas.height).toInt,
              255
            )
          ZIO.succeed(canvas.putPixel(x, y, color)) *>
            ZIO.succeed(canvas.redraw())
        }
      }

  private val program =
    ZIO.succeed(Canvas.create(canvasSettings)).tap { canvas =>
      ZIO.succeed(canvas.fill(Color(255, 255, 0))) *>
        (draw(canvas) *>
          ZIO.succeed(canvas.redraw())).fork
    } <* ZIO.never

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = program

}
