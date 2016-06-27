/**
  * Created by Administrator on 17/06/2016.
  */
case class PurchaseOrderList(val purchaseOrderID: String, val purchasedProducts: Array[Product], val costOfPurchaseOrder: String, var purchaseOrderStatus: String)

  object PurchaseOrderList{

    def increaseStockLevels(purchaseOrderList: PurchaseOrderList,allProds: Array[Product]): Unit = {

      /**
        * with the chosen purchase order.
        * get every product and amount
        * for each product add amount to stock.
        **/

      var allProductsCheck: Array[String] = new Array[String](4)

      //val products : Array[Product] = productOrders.find(_.products == products).get.products

      for (x <- 0 to (allProds.length - 1)) {
        allProductsCheck(x) = allProds(x).productID
      }

      var productCount: Array[Int] = new Array[Int](4)

      for (i <- 0 to (productCount.length - 1)) {

        productCount(i) = 0
      }

      for (j <- 0 to (allProductsCheck.length - 1)) // 0 - 4
      {
        for (i <- 0 to (purchaseOrderList.purchasedProducts.length - 1)) // 0 - 25
        {
          if (purchaseOrderList.purchasedProducts(i).productID == allProductsCheck(j)) {
            productCount(j) = productCount(j) + 1 //for each product it checks how much of each product is in the order
          }
        }

        if (productCount(j) == 0) {

        }
        else if (productCount(j) > 0) {
          println()

          println("Adding " + productCount(j) + " " + allProds(j).productName + "'s to the Inventory System.")

          println("Old stock level - " + allProds(j).stockLevel)
          allProds(j).stockLevel = allProds(j).stockLevel + productCount(j)
          println("New stock level - " + allProds(j).stockLevel)

          purchaseOrderList.purchaseOrderStatus = "Received"
        }
      }
    }

    def porouswarePurchaseOrder(purchaseOrderList: PurchaseOrderList, allProds: Array[Product]): Unit =
    {
      println(Console.RED + "Purchase Order ID : " + purchaseOrderList.purchaseOrderID + Console.RESET)
      println()

      println("Items in Purchase Order that require porousware")
      println()
      getPorouswareProductsAndAmounts(purchaseOrderList,allProds)
    }

    def getPorouswareProductsAndAmounts(purchaseOrderList: PurchaseOrderList, allProds: Array[Product]): Unit =
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
        for(i <- 0 to (purchaseOrderList.purchasedProducts.length - 1))
        {
          if((purchaseOrderList.purchasedProducts(i).productID == allProductsCheck(j)) && purchaseOrderList.purchasedProducts(i).porouswareNeeded == true)
          {
            productCount(j) = productCount(j) + 1
          }
        }
        if(productCount(j) == 0)
        {

        }
        else if(productCount(j) > 0)
        {
          //println("Order " + j)


          println("Product ID - " + allProds(j).productID + ", Name - " + allProds(j).productName + ". Amount of " + allProds(j).productName + "'s that need porousware applied - " + productCount(j))
          println()
        }
      }
    }

  def purchaseOrderInformation(purchaseOrderList: PurchaseOrderList): Unit =
  {
    println(Console.RED + "Purchase Order ID : " + purchaseOrderList.purchaseOrderID + Console.RESET)
    println()

    println("Purchase Order Cost : " + purchaseOrderList.costOfPurchaseOrder)
    println("Purchase Order Status : " + purchaseOrderList.purchaseOrderStatus)
    println()
    println("Items in Purchase Order")
  }

  def getPurchaseOrderProductsAndAmounts(purchaseOrderList: PurchaseOrderList, allProds: Array[Product]): Unit =
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

      for(i <- 0 to (purchaseOrderList.purchasedProducts.length - 1))
      {
        if(purchaseOrderList.purchasedProducts(i).productID == allProductsCheck(j))
        {
          productCount(j) = productCount(j) + 1
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
      }
    }
  }
}