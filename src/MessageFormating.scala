
trait MessageFormating {

  /**
    *   Builds a menu from input strings
    */

  def BuildMenu(items:String*): Unit = {
    println()
    for(i <- 0 to items.length - 1) {
      println((i+1) + " - "+items(i))
    }
    println()
  }

  /**
    * Prints an error message when someone has input something unexpected
    */

  def ErrorMsg(msgType: Int,time: Double, msg:String): Unit = {

    val len: Int = msg.length()

    val charArray: Array[Char] = new Array[Char](len)

    if(msgType == 1) {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.RED + Console.BOLD + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 2)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.GREEN + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 3)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.CYAN + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 4)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.BLUE + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 5)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.RED + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 6)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.GREEN + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 7)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.MAGENTA + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 8)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.YELLOW + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else if(msgType == 9)
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        print(Console.WHITE + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    else
    {
      for (i <- 0 to charArray.length - 1) {
        charArray(i) = msg.charAt(i)
        println(Console.RED + charArray(i))
        Thread.sleep(time.toLong)
      }
    }
    println()
  }
}