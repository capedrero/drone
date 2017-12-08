package com.drone

object Direcciones extends Enumeration {
  type Direccion = Value
  val Arriba, Abajo, Derecha, Izquierda = Value
}


import Direcciones._

import scala.annotation.tailrec
import scala.collection.mutable

class Drone extends Movimientos {

  /**
    * Función solución.
    * Primero obtiene el área del rango con respecto al centro.
    * Segundo hace backtracking hasta encontrar algún elemento de ese área.
    * @param x
    * @param y
    * @param rango
    * @return
    */
  def obtenerUrbanizaciónes(x: Double, y: Double, rango: Int): mutable.ListBuffer[String] = {
    val solucion = mutable.ListBuffer[String]()
    val inicio = obtenerIdentificadorUrbanización(x, y)
    val visitados = mutable.ListBuffer[String](inicio)
    // id13, centro según el enunciado.
    val area = obtenerArea(rango, "id13")
    backtracking(solucion, area, inicio, Seq(Izquierda, Arriba, Derecha, Abajo), visitados)
    solucion ++ area
  }

  /**
    * Dado una posición
    * @param area
    * @param posicion
    * @param rango
    * @param direccion
    * @return
    */
  @tailrec
  final def obtenerAreaUrbanizacion(area: mutable.ListBuffer[String], posicion: String, rango: Int, direccion: Direccion): (mutable.ListBuffer[String], String) = {
    if (rango == 0) {
      (area, posicion)
    } else {
      val newPosicion = obtenerAdyacente(posicion, direccion)
      area += newPosicion
      obtenerAreaUrbanizacion(area, newPosicion, rango - 1, direccion)
    }
  }

  /**
    * Obtiene el nodo de inicio para comenzar a analizar el área.
    * @param rango
    * @param posicion
    * @param direccion
    * @return
    */
  @tailrec
  final def obtenerInicioArea(rango: Int, posicion: String, direccion: Direccion): String = {
    if (rango == 0) {
      posicion
    } else {
      obtenerInicioArea(rango - 1, obtenerAdyacente(posicion, direccion), direccion)
    }
  }

  /**
    * Obtiene el área del rango que se le pasa.
    * @param rango
    * @param inicio
    * @return
    */
  def obtenerArea(rango: Int, inicio: String): mutable.ListBuffer[String] = {
    val area = mutable.ListBuffer[String]()
    val inicioarea = obtenerInicioArea(rango, inicio, Derecha)
    val area1 = obtenerAreaUrbanizacion(area, inicioarea, rango, Abajo)
    val area2 = obtenerAreaUrbanizacion(area1._1, area1._2, rango * 2, Izquierda)
    val area3 = obtenerAreaUrbanizacion(area2._1, area2._2, rango * 2, Arriba)
    val area4 = obtenerAreaUrbanizacion(area3._1, area3._2, rango * 2, Derecha)
    val area5 = obtenerAreaUrbanizacion(area4._1, area4._2, rango, Abajo)
    area5._1
  }




  /**
    * Se ha encontrado con algún elemento de los encontrados en área.
    * @param posicion
    * @param area
    * @return
    */
  def esSolucion(posicion: String, area: mutable.ListBuffer[String]): Boolean = area.contains(posicion)

  /**
    *
    * @param lista
    * @param area
    * @param posicion
    * @param direcciones
    * @param visitados
    */
  def backtracking(lista: mutable.ListBuffer[String],
                   area: mutable.ListBuffer[String],
                   posicion: String,
                   direcciones: Seq[Direccion],
                   visitados: mutable.ListBuffer[String]): Unit = {

    visitados += posicion
    if (esSolucion(posicion, area)) {
      return
    }else{
      lista += posicion
    }
    direcciones.foreach(direccion => {
      val candidato = obtenerAdyacente(posicion, direccion)
      if (!visitados.contains(candidato)) {
        backtracking(lista, area, candidato, direcciones, visitados)
        visitados.filterNot(x=>x.equals(candidato))
      }
    })
  }
}

trait Movimientos {
  def obtenerIdentificadorUrbanización(coordenadaX: Double, coordenadaY: Double): String = ???

  def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = ???
}
