package pl.softwaremill.traffic

object View

trait ViewDefinitionComponent {
  val viewDefinition: ViewDefinition

  trait ViewDefinition {
    val widthPixels: Int
    val heightPixels: Int
    val widthSpan: Span
    val heightSpan: Span
  }
}

trait SpanToPixelsTranslatorComponent {
  this: ViewDefinitionComponent =>

  val spanToPixelsTranslator = new SpanToPixelsTranslator

  class SpanToPixelsTranslator {
    def translate(pos: (Span, Span)): (Int, Int) = {
      val posX = translateToScale(pos._1.mm, viewDefinition.widthSpan.mm, viewDefinition.widthPixels)
      val posY = translateToScale(pos._2.mm, viewDefinition.heightSpan.mm, viewDefinition.heightPixels)
      (posX, posY)
    }

    private def translateToScale(current: Long, max: Long, maxScaled: Int): Int = {
      ((current.toDouble / max.toDouble) * maxScaled.toDouble).toInt
    }
  }
}