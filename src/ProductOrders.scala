/**
  * Created by Ben on 22/06/2016.
  */
object ProductOrders {

  /** Customers products **/
  val dagumisProductOrder:Array[Product] = new Array[Product](50)

  for ( i <- 0 to (dagumisProductOrder.length - 1))
  {
    if(i < 30)
      {
        dagumisProductOrder(i) = Product.findById("0001").get
      }
    else
      {
        dagumisProductOrder(i) = Product.findById("0002").get
      }
  }

  val keisukesProductOrder:Array[Product] = new Array[Product](25)

  for ( i <- 0 to (keisukesProductOrder.length - 1))
  {
    keisukesProductOrder(i) = Product.findById("0002").get
  }

  val rytisProductOrder:Array[Product] = new Array[Product](5)

  for ( i <- 0 to (4))
  {
    rytisProductOrder(i) = Product.findById("0003").get
  }

  val snoopLionsProductOrder:Array[Product] = new Array[Product](2)

  for ( i <- 0 to (1))
  {
    snoopLionsProductOrder(i) = Product.findById("0004").get
  }

  val individualProductOrders = Array(dagumisProductOrder,keisukesProductOrder,rytisProductOrder,snoopLionsProductOrder)

}
