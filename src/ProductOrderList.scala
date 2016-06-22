/**
  * Created by Administrator on 17/06/2016.
  **/
case class ProductOrderList(override val orderID: String, override val customerID: String, override val customerName: String, override val customerAddress: String, val products: Array[Product],
                            var checkedOutStatus : Boolean, var checkedOutBy: String, var orderStatus: String) extends Order(orderID, customerID, customerName, customerAddress)

  object ProductOrderList {

    /** var productOrders = Seq(productOrders("1086", "0001", "Dagumi Fujiwara", "86 Akina, Mount Haruna, Japan",getCustomerProducts()/**products dagumisProductOrder **/,false,"",""),
      * productOrders("1007", "0007", "Keisuke Takahashi", "86 Akina, Mount Haruna, Japan",getCustomerProducts()/**products keisukesProductOrder **/,false,"",""),
      * productOrders("1791", "0002", "Rytis the Grebe Murderer", "Prison",getCustomerProducts()/**products rytisProductOrder **/,false,"",""),
      * productOrders("1420", "0003", "Snoop Lion", "Bakersfield, CA, USA" ,getCustomerProducts()/**products snoopLionsProductOrder **/,false,"","")) **/

    var productOrders = Array(ProductOrderList("1086", "0001", "Dagumi Fujiwara", "86 Akina, Mount Haruna, Japan",ProductOrders.dagumisProductOrder, false, "", ""),
      ProductOrderList("1007", "0007", "Keisuke Takahashi", "86 Akina, Mount Haruna, Japan", ProductOrders.keisukesProductOrder, false, "", ""),
      ProductOrderList("1791", "0002", "Rytis the Grebe Murderer", "Prison", ProductOrders.rytisProductOrder, false, "", ""),
      ProductOrderList("1420", "0003", "Snoop Lion", "Bakersfield, CA, USA", ProductOrders.snoopLionsProductOrder, false, "", ""))

    def findByOrderId(orderID: String) = productOrders.find(_.orderID == orderID)

    /**val x = orderID
    val y = customerID
    val z = customerName
    val a = customerAddress

      def getOrderID(): String = {
      x
    }

    def getCustomerID(): String = {
      y
    }

    def getCustomerName(): String = {
      z
    }
      **/

    def getOrderID(orderID : String) = productOrders.find(_.orderID == orderID)

    def getCustomerID(customerID : String) = productOrders.find(_.customerID == customerID).get.customerID

    def getCustomerName(customerName : String) = productOrders.find(_.customerName == customerName).get.customerName


    def printOrderInformation(): Unit = {

      val orderID = ""
      val customerID  = ""
      val customerName = ""
      val customerAddress = ""

      println("Order ID : " + productOrders.find(_.orderID == orderID).get.orderID)
      println("Customer ID : " + productOrders.find(_.customerID == customerID).get.customerID)
      println("Customer Name : " + productOrders.find(_.customerName == customerName).get.customerName)
      println("Customer Address : " + productOrders.find(_.customerAddress == customerAddress).get.customerAddress)
    }

    def getCustomerProducts(productOrderList: ProductOrderList): Unit = {
      println
      println("Customer's products : ")

      //val products : Array[Product] = productOrders.find(_.products == products).get.products

      for (i <- 0 to (productOrderList.products.length - 1)) {
        println((i + 1) + " - " + productOrderList.products(i).productName)
      }
      println
    }

    def decrementStockLevels(productOrderList: ProductOrderList,allProds: Array[Product]): Unit = {

      /**
        * with the chosen order.
        * get every product and amount
        * for each product remove amount from stock.
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
        for (i <- 0 to (productOrderList.products.length - 1)) // 0 - 25
        {
          if (productOrderList.products(i).productID == allProductsCheck(j)) {
            productCount(j) = productCount(j) + 1 //for each product it checks how much of each product is in the order
          }
        }

        if (productCount(j) == 0) {

        }
        else if (productCount(j) > 0) {
          println()

          println("Decrementing " + productCount(j) + " " + allProds(j).productName + "'s from the Inventory System.")

          println("Old stock level - " + allProds(j).stockLevel)
          allProds(j).stockLevel = allProds(j).stockLevel - productCount(j)
          println("New stock level - " + allProds(j).stockLevel)
          println()
        }
      }
    }

    def getProductsAndAmounts(productOrderList: ProductOrderList,allProds: Array[Product]): Unit = {

      var allProductsCheck: Array[String] = new Array[String](4)

      for (x <- 0 to (allProds.length - 1)) {
        allProductsCheck(x) = allProds(x).productID
      }

      var productCount: Array[Int] = new Array[Int](4)

      for (i <- 0 to (productCount.length - 1)) {

        productCount(i) = 0
      }

      for (j <- 0 to (allProductsCheck.length - 1)) // 0 - 4
      {
        for (i <- 0 to (productOrderList.products.length - 1)) // 0 - 25
        {
          if (productOrderList.products(i).productID == allProductsCheck(j)) {
            productCount(j) = productCount(j) + 1 //for each product it checks how much of each product is in an order
          }
        }

        if (productCount(j) == 0) {

        }
        else if (productCount(j) > 0) {
          //println("Order " + j)
          println()

          println("Product Details - ID - " + allProds(j).productID + ", Name - " + allProds(j).productName)
          println()

          println("Amount - " + productCount(j))
          println()
        }
      }
    }

    /**def getStatus(): Unit = {

      if (checkedOutBy == "") {
        println("Order Checked out : " + checkedOutStatus)
        println
      }
      else {
        println("Order Checked out by " + checkedOutBy)
        println()
      }
    }**/
  }


  //**/

//}