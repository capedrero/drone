package com.drone

import com.drone.Direcciones.Direccion
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable.ListBuffer
import Direcciones._
class TestDrone extends WordSpec with Matchers with MockFactory {

  private def getId(i: Int) = {
    "id" + i
  }
  /**
    * Obtiene el mock para los movimientos del area
    * @param mockMovimientos
    * @param repeat
    */
  private def mockMovimientosArea(mockMovimientos: Movimientos, repeat: Int): Unit = {
    var i: Int = 100
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning(getId(i)).repeat(repeat)
    i += 1
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning(getId(i)).repeat(repeat)
    i += 1
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning(getId(i)).repeat(repeat * 2)
    i += 1
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning(getId(i)).repeat(repeat * 2)
    i += 1
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning(getId(i)).repeat(repeat * 2)
    i += 1
    (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning(getId(i)).repeat(repeat)

  }
  "Drone Test, obtenerAdyacente" should {


    "rango 1, area con size 8" in {
      val drone = new Drone() {
        val mockMovimientos = mock[Movimientos]
        mockMovimientosArea(mockMovimientos, 1)

        override def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = mockMovimientos.obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion)
      }
      drone.obtenerArea(1, "id13").size shouldBe 8
    }

    "rango 2, area con size 16" in {
      val drone = new Drone() {
        val mockMovimientos = mock[Movimientos]
        mockMovimientosArea(mockMovimientos, 2)

        override def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = mockMovimientos.obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion)
      }
      drone.obtenerArea(2, "id13").size shouldBe 16

    }

    "rango 3, area con size 8" in {
      val drone = new Drone() {
        val mockMovimientos = mock[Movimientos]
        mockMovimientosArea(mockMovimientos, 3)

        override def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = mockMovimientos.obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion)
      }
      drone.obtenerArea(3, "id13").size shouldBe 24

    }

  }


  "Drone Test, obtenerUrbanizaciónes" should {
    "inicio fuera del area" in {
      val mockMovimientos = mock[Movimientos]
      var i: Int = 1
      // Movimientos 69->izq199->arr200->arr5
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id199").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning("id69").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id199").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id200").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id199").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id5").anyNumberOfTimes()
      (mockMovimientos.obtenerIdentificadorUrbanización(_: Double, _: Double)).expects(*, *).returning("id69").repeat(1)

      val drone = new Drone(){
        override def obtenerArea(rango: Int, inicio: String): ListBuffer[String] = ListBuffer[String]("id1", "id2", "id3")

        override def obtenerIdentificadorUrbanización(coordenadaX: Double, coordenadaY: Double): String = mockMovimientos.obtenerIdentificadorUrbanización(coordenadaX, coordenadaY)

        override def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = mockMovimientos.obtenerAdyacente(identificadorUrbanizaciónOrigen, direccion)
      }


      drone.obtenerUrbanizaciónes(10.8, 5.0, 1) shouldEqual ListBuffer("id69", "id199", "id200", "id1", "id2", "id3")
    }


    "inicio dentro del area" in {
      val mockMovimientos = mock[Movimientos]
      //Movimientos (id69, izqid199, arrid200, derid201, arrid202, derid203, abajid204, derid205, derid206, abajid300, abajid301, abajoid1, id1, id2, id3)
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id199").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id200").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning("id201").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id69").once()

      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id200").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id202").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning("id203").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id204").once()

      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id201").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id203").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning("id205").once()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id300").once()

      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Izquierda).returning("id69").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Arriba).returning("id69").anyNumberOfTimes()
      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Derecha).returning("id206").anyNumberOfTimes()

      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id301").once()

      (mockMovimientos.obtenerAdyacente(_: String, _: Direccion)).expects(*, Abajo).returning("id1").anyNumberOfTimes()


      (mockMovimientos.obtenerIdentificadorUrbanización(_: Double, _: Double)).expects(*, *).returning("id69").repeat(1)
      val drone = new Drone(){
        override def obtenerArea(rango: Int, inicio: String): ListBuffer[String] = ListBuffer[String]("id1", "id2", "id3")

        override def obtenerIdentificadorUrbanización(coordenadaX: Double, coordenadaY: Double): String = mockMovimientos.obtenerIdentificadorUrbanización(coordenadaX, coordenadaY)

        override def obtenerAdyacente(identificadorUrbanizaciónOrigen: String, direccion: Direccion): String = mockMovimientos.obtenerAdyacente(identificadorUrbanizaciónOrigen, direccion)
      }


      drone.obtenerUrbanizaciónes(10.8, 5.0, 1) shouldEqual ListBuffer("id69", "id199", "id200", "id201", "id202", "id203", "id204", "id205", "id206", "id300", "id301", "id1", "id2", "id3")
    }
  }
}
