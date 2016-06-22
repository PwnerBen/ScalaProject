



object AllProducts {

  //val arrayOfAllProducts:Array[Product] = new Array[Product](4)

  val arrayOfAllProducts = Array(
    Product.findById("0001").get,
    Product.findById("0002").get,
    Product.findById("0003").get,
    Product.findById("0004").get)

  def getAllProducts(): Array[Product] ={

    arrayOfAllProducts
  }
}
