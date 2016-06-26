object Project extends MessageFormating{

  def main(args: Array[String]) {
    println("NB GARDENS WAREHOUSE ORDER TRACKING SYSTEM")
    println()

    EmployeeManagement.warehouseLogin()

  }

  def mainMenu(loggedInEmployee : String): Unit ={

    println()
    println("What are you trying to manage?")

    BuildMenu("Orders","Stock","Exit")

    val choiceInput = scala.io.StdIn.readLine()
    choiceInput match {
      case "1" => println("Loading Orders...")
        println()
        NbGardensOrderList(ProductOrderList.productOrders, loggedInEmployee)

      case "2" => println("Loading Stock...")
        println()
        stockManagement(AllProducts.arrayOfAllProducts,ProductOrderList.productOrders,loggedInEmployee,PurchaseOrders.purchaseOrders)

      case "3" => println("Goodbye " + loggedInEmployee + "!")
        //continue = false

      case _ => errorMsg(loggedInEmployee)
        mainMenu(loggedInEmployee)
    }
  }

  def stockManagement(productArray: Array[Product], pArray: Array[ProductOrderList],loggedEmp: String, purchaseOrderArray: Array[PurchaseOrderList])
  {
    println("NB Gardens Stock List!")
    println()

    if(showStockList) {
      displayStock(productArray)
    }

    println("What would you like to do with the Stock System?")

    BuildMenu("Decrement stock levels from checked out order(s) ","Update Inventory System from purchase order(s) ","Inform accounts of received purchase order(s) ","Show / Hide Stock List ",
      "Find a product ","See what items need porousware from received purchase order(s)","Go back")

    val stockChoice = scala.io.StdIn.readLine()

    stockChoice match {
      case "1" => println("Loading Inventory System...")

        decrementStockFromCheckedOrders(pArray,loggedEmp,productArray,purchaseOrderArray)

      case "2" => println("Loading Inventory System...")

        updateInventoryFromPurchaseOrder(productArray, pArray,loggedEmp,purchaseOrderArray)

        println()

      case "3" => println("Loading Message System...")
        println()
        informAccounts(productArray,pArray,loggedEmp,purchaseOrderArray)

      case "4" =>
        if (showStockList) {
          showStockList = false
        }
        else {
          showStockList = true
        }
        println()
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)

      case "5" => println("Loading Product Finder...")
        println()
        productFinder(productArray,pArray,loggedEmp,purchaseOrderArray)
      case "6" => println("Loading porousware options...")
        println()
        porouswareCheck(purchaseOrderArray,productArray)

        println()
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)

      case "7" => println("Loading options...")
        println()
        mainMenu(loggedEmp)

      case _ => errorMsg(loggedEmp)
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
    }
  }

  def porouswareCheck(purchaseOrderArray: Array[PurchaseOrderList], allProds: Array[Product]): Unit =
  {
    /**
      * For each purchase order I want to go through each product see if they need porousware and get the amounts needed.
      */
    var found = false

    for(i <- 0 to purchaseOrderArray.length - 1){

      if(purchaseOrderArray(i).purchaseOrderStatus == "Received" || purchaseOrderArray(i).purchaseOrderStatus == "Sent to accounts") {
        found = true
        PurchaseOrderList.porouswarePurchaseOrder(purchaseOrderArray(i), allProds)
      }
    }
    if(!found)
    {
      println("There are currently no received purchase orders.")
    }
    println("Enter to continue...")
    scala.io.StdIn.readLine()
  }

  def sendMessageToAccounts(): Unit =
  {
    println("Sending stock list to accounts")
  }

  def updateInventoryFromPurchaseOrder(allProductsArray: Array[Product],pArray: Array[ProductOrderList],loggedEmp: String, purchaseOrderArray: Array[PurchaseOrderList]): Unit =
  {
    println()
    println("NB Gardens Purchase Orders")
    println()

    displayPurchaseOrders(purchaseOrderArray,pArray, loggedEmp, allProductsArray)
  }

  def displayPurchaseOrders(purchaseOrderArray: Array[PurchaseOrderList],pArray: Array[ProductOrderList], loggedEmp: String, allProds: Array[Product]): Unit =
  {
    for ( i <- 0 to (purchaseOrderArray.length - 1))
    {

      PurchaseOrderList.purchaseOrderInformation(purchaseOrderArray(i))

      if(showPurchaseOrders) {
        PurchaseOrderList.getPurchaseOrderProductsAndAmounts(purchaseOrderArray(i), allProds)
      }
      println()

    }

    println("What would you like to do with these Purchase Orders?")
    BuildMenu("Add Purchase Order to system.", "Go back to the Stock List", "Go back to main menu", "Show / Hide Purchase Order details")

    val stockChoice = scala.io.StdIn.readLine()

    stockChoice match {
      case "1" =>
        addPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)

      case "2" => stockManagement(allProds,pArray,loggedEmp,purchaseOrderArray)

      case "3" =>
        mainMenu(loggedEmp)

      case "4" =>
        if (showPurchaseOrders) {
          showPurchaseOrders = false
        }
        else {
          showPurchaseOrders = true
        }
        println()
        updateInventoryFromPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)

      case _ => errorMsg(loggedEmp)
        updateInventoryFromPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)
    }
  }

  def informAccounts(productArray: Array[Product],pArray: Array[ProductOrderList],loggedEmp: String,purchaseOrderArray: Array[PurchaseOrderList]): Unit ={

    var found = false

    println("Received Purchase Orders")
    println()

    for ( i <- 0 to (purchaseOrderArray.length - 1))
    {
      if(PurchaseOrders.findByPOID(purchaseOrderArray(i).purchaseOrderID).get.purchaseOrderStatus == "Received"){

        println("Purchase Order : " + purchaseOrderArray(i).purchaseOrderID)
        println()
        found = true
      }

    }
    if(found)
      {
        println("Send received Purchase Order(s) details to accounts? Y/N")

        val input = scala.io.StdIn.readLine()

        if(input == "Y" || input == "y" || input == "yes" || input == "Yes")
        {
          println()
          print(Console.GREEN + "Sending")
          ErrorMsg(2,500,"....")
          println(Console.RESET)

          for ( i <- 0 to (purchaseOrderArray.length - 1))
          {
            if(PurchaseOrders.findByPOID(purchaseOrderArray(i).purchaseOrderID).get.purchaseOrderStatus == "Received"){

              println("Purchase Order " + purchaseOrderArray(i).purchaseOrderID + " details sent.")
              println()
              PurchaseOrders.findByPOID(purchaseOrderArray(i).purchaseOrderID).get.purchaseOrderStatus = "Sent to accounts"
            }
          }
          println("All received Purchase Order details have been sent to accounts.")
          println()
          println("Enter to continue...")
          scala.io.StdIn.readLine()
          println()

          stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
        }
        if(input == "N" || input == "n" || input == "no" || input == "No")
        {
          stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
        }
      }
    else
    {
      println("No received Purchase Orders to send to accounts.")
      println()
      println("Enter to continue...")
      scala.io.StdIn.readLine()
      stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
    }
  }

  def addPurchaseOrder(allProds: Array[Product],pArray: Array[ProductOrderList],loggedEmp: String,purchaseOrderArray: Array[PurchaseOrderList]): Unit ={

    println("Which Purchase Order would you like to add to the system? Enter the Purchase Order ID")
    val purchaseOrderChoice = scala.io.StdIn.readLine()

    if(PurchaseOrders.findByPOID(purchaseOrderChoice).isEmpty)
      {
        println("Purchase Order ID not found..")
        println("Enter to continue...")
        scala.io.StdIn.readLine()
        updateInventoryFromPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)
      }
    else{

      if(PurchaseOrders.findByPOID(purchaseOrderChoice).get.purchaseOrderStatus == "In Transit")
        {
          PurchaseOrderList.increaseStockLevels(PurchaseOrders.findByPOID(purchaseOrderChoice).get,allProds)
          println()
          println("Purchase Order Added..")
          println()
          println("Enter to continue...")
          scala.io.StdIn.readLine()

          updateInventoryFromPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)
        }
      else{

        println("Purchase Order has already been added to the Inventory system.")
        println("Enter to continue...")
        scala.io.StdIn.readLine()
        updateInventoryFromPurchaseOrder(allProds,pArray,loggedEmp,purchaseOrderArray)
      }
    }
  }

  def decrementStockFromCheckedOrders(pArray: Array[ProductOrderList],loggedEmp: String, allProductsArray: Array[Product],purchaseOrderArray: Array[PurchaseOrderList]): Unit =
  {
    var found = false

    var checkedOrders = 0

    var totalProductsInCheckedOrders = 0

    println()
    println(loggedEmp + "'s Checked Out Orders")
    println()

    for (i <- 0 to (pArray.length - 1)) {
      if (loggedEmp == pArray(i).checkedOutBy && (pArray(i).orderStatus == "Pending")) {
        checkedOrders = checkedOrders + 1

        totalProductsInCheckedOrders = totalProductsInCheckedOrders + pArray(i).products.length

      }
    }
    if (checkedOrders == 0) {
      println(loggedEmp + ", you do not have any checked orders to decrement the Inventory System.")
      println()
      println("Enter to continue...")
      scala.io.StdIn.readLine()

      stockManagement(allProductsArray,pArray,loggedEmp,purchaseOrderArray)
    }
    else if (checkedOrders > 0) {
      println("You have " + checkedOrders + " checked out order(s).")
      println()
      println("Total Product Amount from all checked orders - " + totalProductsInCheckedOrders)
      println()

        println(Console.RED + "Checked out orders.." + Console.RESET)
        println()

        for (i <- 0 to (pArray.length - 1)) {
          if (loggedEmp == pArray(i).checkedOutBy && pArray(i).orderStatus == "Pending") {
            println("Order " + pArray(i).orderID)
            println()
            println("Products")

            ProductOrderList.getProductsAndAmounts(pArray(i),allProductsArray)
          }
        }

        println("Would you like to decrement an order from the Inventory System? Y/N")
        val userContinueChoice = scala.io.StdIn.readLine()

        if(userContinueChoice == "Y" || userContinueChoice == "y" || userContinueChoice == "yes" || userContinueChoice == "Yes")
        {
          println()
          println("Which checked order would you like to decrement from the Inventory System?")
          println("Enter the Order ID")

          val userInputOrderID = scala.io.StdIn.readLine()

          for (i <- 0 to (pArray.length - 1)) // for all of the product orders
          {
            if ((loggedEmp == pArray(i).checkedOutBy) && userInputOrderID == pArray(i).orderID && (pArray(i).orderStatus == "Pending")) // check if it has been checked out by employee
            {
              // and if the user input was one of their orders
              //pArray(i).decrementStockLevels(allProductsArray)

              if(ProductOrderList.decrementStockLevels(pArray(i),allProductsArray)){
                pArray(i).orderStatus = "Packed"
              }
              found = true

              println()
              println("Enter to continue...")
              scala.io.StdIn.readLine()
              decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)

            }
            else if(pArray(i).orderStatus == "Packed" && userInputOrderID == pArray(i).orderID)
            {
              if(loggedEmp == pArray(i).checkedOutBy)
              {
                println("You have already decremented this order.")
                found = true

                println()
                println("Enter to continue...")
                scala.io.StdIn.readLine()
                decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)

              }
              else
              {
                println("This order has already been decremented from the Inventory System by another warehouse worker.")
                found = true
                println()
                println("Enter to continue...")
                scala.io.StdIn.readLine()
                decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)
              }

            }
            else if(loggedEmp != pArray(i).checkedOutBy && userInputOrderID == pArray(i).orderID)
            {
              println("You have not checked out this order.")
              found = true
              println()
              println("Enter to continue...")
              scala.io.StdIn.readLine()
              decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)
            }
            else if(userInputOrderID != pArray(i).orderID)
            {

            }
          }
          if(!found)
          {
            println("Order not found.")
            //found = false
            println()
            println("Enter to continue...")
            scala.io.StdIn.readLine()
            decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)
          }

          //exit = false
        }
        else if(userContinueChoice == "N" || userContinueChoice == "n" || userContinueChoice == "no" || userContinueChoice == "No")
        {
          println()
          stockManagement(allProductsArray,pArray,loggedEmp,purchaseOrderArray)
        }
        else
        {
          println("Sorry that input wasn't recognized.. ")
          println()
          decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)

          //exit = false
        }
      }
  }

  def productFinder(productArray: Array[Product],pArray: Array[ProductOrderList],loggedEmp : String, purchaseOrderArray: Array[PurchaseOrderList]): Unit =
  {
    var productFound = false

    println("Which product would you like to locate?")
    println("Enter product name or product ID..")
    println()

    val productChoice = scala.io.StdIn.readLine()

    for( i <- 0 to (productArray.length - 1))
    {
      if (productChoice == productArray(i).productName) {
        println("The " + productArray(i).productName + " is located at section " + productArray(i).productLocation + " of the warehouse.")
        println("Warehouse maps can be located at the end of each aisle")

        productFound = true
      }
      else if (productChoice == productArray(i).productID) {
        println("Product " + productArray(i).productID + " is located at section " + productArray(i).productLocation + " of the warehouse.")
        println("Warehouse maps can be located at the end of each aisle")

        productFound = true
      }
    }
    if (productFound == false)
    {
      println("Product not found...")
    }

    findOtherProduct(productArray,pArray,loggedEmp,purchaseOrderArray)
  }

  def findOtherProduct(productArray: Array[Product],pArray : Array[ProductOrderList],loggedEmp : String, purchaseOrderArray : Array[PurchaseOrderList])
  {

    println()
    println("Would you like to find another product? Y/N ")

    val productChoice2 = scala.io.StdIn.readLine()

    if(productChoice2 == "Y" || productChoice2 == "y" || productChoice2 == "yes" || productChoice2 == "Yes")
    {
      println()
      productFinder(productArray,pArray,loggedEmp,purchaseOrderArray)

    }
    else if(productChoice2 == "N" || productChoice2 == "n" || productChoice2 == "no" || productChoice2 == "No")
    {
      println()
      stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
    }
    else
    {
      println("Sorry that input wasn't recognized.. ")
      println()
      findOtherProduct(productArray,pArray,loggedEmp,purchaseOrderArray)

    }
  }

  def displayStock(productArray: Array[Product])
  {
    for ( i <- 0 to (productArray.length - 1))
    {
      println("Product ID : " + productArray(i).productID)
      println("Product Name : " + productArray(i).productName)
      println("Product Location : " + productArray(i).productLocation)
      println("Porousware needed : " + productArray(i).porouswareNeeded)
      println("Stock Level : " + productArray(i).stockLevel)

      println()
    }
  }

  var break = false

  def orderSelection(input : String, pArray : Array[ProductOrderList], loggedEmp : String): Unit =
  {
    if (findOrder(input, pArray)) {

      println("What would you like to do with this customer order?")

      BuildMenu("Check out order","Go back to all orders","Go back to main menu","Show / Hide Customer Products")

      val input2 = scala.io.StdIn.readLine()
      input2 match {
        case "1" => checkoutOrder(input, pArray, loggedEmp)
          orderSelection(input,pArray,loggedEmp)
        case "2" => println("Loading orders...")
          println()
          NbGardensOrderList(pArray,loggedEmp)
          //openAnOrder(pArray,loggedEmp)
        case "3" => println("Loading menu...") //"Goodbye " +  loggedEmp +"!")
          mainMenu(loggedEmp)
        case "4" =>
          if (showCustomerProducts) {
            showCustomerProducts = false
          }
          else {
            showCustomerProducts = true
          }
          orderSelection(input,pArray,loggedEmp)
        case _ => errorMsg(loggedEmp)

      orderSelection(input,pArray,loggedEmp)
      }
    }
    else {
      //openAnOrder(pArray,loggedEmp )
      NbGardensOrderList(pArray,loggedEmp)
    }
  }

  def errorMsg(loggedEmp:String): Unit =
  {
    ErrorMsg(1,70,"ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN")
    println()
    ErrorMsg(0,1000,"54321")
    //ErrorMsg(1,400,"....")
    println("BOOOOOOOOOM!!")
    println()
    ErrorMsg(1,50,"The warehouse has now been destroyed thanks to you inputting something silly. "+
      "I hope you're proud of yourself... Just look at what you've done " + loggedEmp)
    println(Console.RESET)
    Thread.sleep(4000)
  }

  def openAnOrder(pArray: Array[ProductOrderList], loggedEmp: String){//: Boolean = {
    println("Select which order to open..")

    val input = scala.io.StdIn.readLine()

    orderSelection(input, pArray, loggedEmp)
  }

  var showCustomerOrders = true
  var showCustomerProducts = true
  var showStockList = true
  var showPurchaseOrders = true

  def NbGardensOrderList(pArray: Array[ProductOrderList],loggedEmp: String)
  {
    println
    println("NB Gardens Order List!")
    println

    if(showCustomerOrders){
      displayOrders(pArray)
    }

    println("What would you like to do?")

    BuildMenu("Open an order","Open my checked out orders","Go back","Show / Hide the Customer Order List","Update checked out customer orders","Send delivery report to accounts")

    val userChoice = scala.io.StdIn.readLine()

    userChoice match {
      case "1" =>
        println()
         openAnOrder(pArray,loggedEmp)

      case "2" => openCheckedOutOrders(pArray,loggedEmp)
        println()

      case "3" =>
        mainMenu(loggedEmp)

      case "4" =>
        if(showCustomerOrders)
        {
          showCustomerOrders = false
        }
        else
        {
          showCustomerOrders = true
        }
        NbGardensOrderList(pArray,loggedEmp)

      case "5" =>

        updateCustomerOrders(pArray,loggedEmp)

      case "6" =>

        msgAccounts(pArray)
        NbGardensOrderList(pArray,loggedEmp)

      case _ => NbGardensOrderList(pArray,loggedEmp)
    }
  }

  def msgAccounts(pArray: Array[ProductOrderList]): Unit ={

    var x = 0

    def filter(listOrders1: List[ProductOrderList]){
      def process(listOrder2: List[ProductOrderList]): List[ProductOrderList] ={

        if(listOrder2.isEmpty) listOrder2
        else if (listOrder2.head.orderStatus == "Delivered" ){

          x = x + 1
          listOrder2.head :: process(listOrder2.tail)
        }
        else
          process(listOrder2.tail)
      }
      process(listOrders1)
    }
    filter(ProductOrderList.productOrders.toList)

    if(x > 0) {
      println()
      println("Would you like to send a delivery report to accounts Y/N")
      val input = scala.io.StdIn.readLine()

      if (input == "Y" || input == "y" || input == "yes" || input == "Yes") {
        println()
        sendReportToAccounts(pArray)
        println()
        println("Enter to continue...")
        scala.io.StdIn.readLine()
      }
      else if (input == "N" || input == "n" || input == "no" || input == "No") {

      }
    }
    else
    {
      println("There isn't a delivery report to send. Accounts is up to date with all delivered orders..")
      println("Enter to continue...")
      scala.io.StdIn.readLine()
    }
  }

  def sendReportToAccounts(pArray: Array[ProductOrderList]): Unit ={

    def filter(listOrders1: List[ProductOrderList]){
      def process(listOrder2: List[ProductOrderList]): List[ProductOrderList] ={

        if(listOrder2.isEmpty) listOrder2
        else if (listOrder2.head.orderStatus == "Delivered" ){

          print(Console.GREEN + "Sending")
          ErrorMsg(2,500,"....")
          println(Console.RESET)

          println("Customer Order - " + ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderID + " delivery details sent.")

          ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderStatus = "Completed"

          listOrder2.head :: process(listOrder2.tail)
        }
        else
          process(listOrder2.tail)
      }
      process(listOrders1)
    }
    filter(ProductOrderList.productOrders.toList)

  }

  def updateCustomerOrders(pArray: Array[ProductOrderList],loggedEmp: String): Unit =
  {
    var x = 0

    def filter(listOrders1: List[ProductOrderList]){
      def process(listOrder2: List[ProductOrderList]): List[ProductOrderList] ={

        if(listOrder2.isEmpty) listOrder2
        else if (listOrder2.head.orderStatus == "Packed" ){

          x = x + 1
          listOrder2.head :: process(listOrder2.tail)
        }
        else
          process(listOrder2.tail)
      }
      process(listOrders1)
    }
    filter(ProductOrderList.productOrders.toList)

    if(x > 0) {
      println()
      println("Would you like to update your checked out orders from packed to delivered? Y/N")
      val input = scala.io.StdIn.readLine()

      if (input == "Y" || input == "y" || input == "yes" || input == "Yes") {
        println()
        updateToDelivered(pArray)
        println()
        println("Enter to continue...")
        scala.io.StdIn.readLine()
      }
      else if (input == "N" || input == "n" || input == "no" || input == "No") {

      }
    }
    else
      {
        println("You don't have any checked out orders or the products that are on your checked out orders haven't been taken from the inventory system yet.")
        println("Enter to continue...")
        scala.io.StdIn.readLine()
      }
    NbGardensOrderList(pArray, loggedEmp)
  }

  def updateToDelivered(pArray: Array[ProductOrderList]): Unit =
  {
    //wanna loop through customer orders, find if any are packed and change to delivered

    def filter(listOrders1: List[ProductOrderList]){
      def process(listOrder2: List[ProductOrderList]): List[ProductOrderList] ={

        if(listOrder2.isEmpty) listOrder2
        else if (listOrder2.head.orderStatus == "Packed" ){

          ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderStatus = "Delivered"

          println("Customer Order - " + ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderID + ": Status - " + ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderStatus)

          listOrder2.head :: process(listOrder2.tail)
        }
        else
          process(listOrder2.tail)
      }
      process(listOrders1)
    }
    filter(ProductOrderList.productOrders.toList)
  }

  def displayOrders(pArray: Array[ProductOrderList])
  {
    for ( i <- 0 to (pArray.length - 1))
    {
      println(pArray(i).orderID + " " + pArray(i).customerName)
      println()
    }
  }

  def findOrder(userInput: String,pArray: Array[ProductOrderList]) : Boolean =
  {
    var found = false

    if(ProductOrderList.findByOrderID(userInput).isEmpty) {

      println("Order ID not found..")
      println("Enter to continue...")
      scala.io.StdIn.readLine()
      println()

      found
    }
    else {

      println("Order " + userInput + " found!")
      println()
      found = true

      val order = ProductOrderList.findByOrderID(userInput)
      println(order.get.orderID)

      println("Order ID : " + order.get.orderID)
      println("Customer ID : " + order.get.customerID)
      println("Customer Name : " +  order.get.customerName)
      println("Customer Address : " +  order.get.customerAddress)

      if (showCustomerProducts) {
        ProductOrderList.getCustomerProducts(order.get)
      }

      if (order.get.checkedOutBy == "") {
        println("Order Checked out : " + order.get.checkedOutStatus)
        println
      }
      else {
        println("Order Checked out by " + order.get.checkedOutBy)
        println()
      }

      found
    }
  }

  def openCheckedOutOrders(pArray: Array[ProductOrderList],loggedInEmp: String)
  {
    println()
    println("Checked out orders for " + loggedInEmp)
    println()

    var x = 0

    if(ProductOrderList.findByCheckedOutBy(loggedInEmp).isEmpty) {

      println("You have no checked out orders.")
      println()
    }
    else
    {
      def filter(listOrders1: List[ProductOrderList]){
        def process(listOrder2: List[ProductOrderList]): List[ProductOrderList] ={

          if(listOrder2.isEmpty) listOrder2
          else if (listOrder2.head.checkedOutBy == loggedInEmp){

            x = x + 1
            println(x + " - Order ID - " + ProductOrderList.findByOrderID(listOrder2.head.orderID).get.orderID + " Customer Name - " + ProductOrderList.findByOrderID(listOrder2.head.orderID).get.customerName)

            listOrder2.head :: process(listOrder2.tail)
          }
          else
            process(listOrder2.tail)
        }
        process(listOrders1)
      }
      filter(ProductOrderList.productOrders.toList)

    }
    NbGardensOrderList(pArray,loggedInEmp)
  }

  def checkoutOrder(userInput: String,pArray: Array[ProductOrderList], loggedInEmp: String)
  {
    for ( i <- 0 to (pArray.length - 1)) {

      if (userInput == pArray(i).orderID) {

        if((pArray(i).checkedOutStatus == false) && (pArray(i).orderStatus != "Pending")){

          pArray(i).checkedOutStatus = true
          pArray(i).checkedOutBy = loggedInEmp
          pArray(i).orderStatus = "Pending"

          println()
          println("Order - " + pArray(i).orderID + " Checked out!")
          println()
        }
        else if(pArray(i).checkedOutBy == loggedInEmp)
        {
          println()
          println(loggedInEmp + ", you have already checked out this order..")
          println()
          println("Enter to continue...")
          scala.io.StdIn.readLine()
        }
        else
        {
            println("Order is already checked out by another Warehouse worker.")
        }
      }
    }
  }
}