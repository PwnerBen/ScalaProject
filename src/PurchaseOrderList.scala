/**
  * Created by Administrator on 17/06/2016.
  */
class PurchaseOrderList(val purchaseOrderID: String, val purchasedProducts: Array[Product], val costOfPurchaseOrder: String, val purchaseOrderStatus: String) {

  def increaseStockLevels(allProds: Array[Product]): Unit ={


  }

  def purchaseOrderInformation(): Unit =
  {
    println("Purchase Order ID : " + purchaseOrderID)
    println("Items in Purchase Order")
    println()

    println("Purchase Order Cost : " + costOfPurchaseOrder)
    println("Purchase Order Status : " + purchaseOrderStatus)
  }

  def getPurchaseOrderProductsAndAmounts(allProds: Array[Product]): Unit =
  {
    var allProductsCheck:Array[String] = new Array[String](4)

    for( x <- 0 to (allProds.length - 1))
    {
      allProductsCheck(x) = allProds(x).productID
    }

    var productCount: Array[Int] = new Array[Int](4)

    for (i <- 0 to (productCount.length - 1)){

      productCount(i) = 0
    }

    for(j <- 0 to (allProductsCheck.length - 1)) // 0 - 4
    {
      //println(allProductsCheck(j))

      for(i <- 0 to (purchasedProducts.length - 1))
      {
        if(purchasedProducts(i).productID == allProductsCheck(j))
        {
          productCount(j) = productCount(j) + 1
          //println(productCount(j))
        }
      }
      if(productCount(j) == 0)
      {

      }
      else if(productCount(j) > 0)
      {
        //println("Order " + j)
        println()

        println("Product Details - ID - " + allProds(j).productID + ", Name - " + allProds(j).productName)
        println()

        println("Amount - " + productCount(j))
        println()
      }
    }
  }
}