
trait MessageFormating {
  def BuildMenu(items:String*): Unit = {
    println()
    for(i <- 0 to items.length - 1) {
      println((i+1) + " - "+items(i))
    }
    println()
  }
}