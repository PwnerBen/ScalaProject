object Project {

  def main(args: Array[String]) {
    println("NB GARDENS WAREHOUSE ORDER TRACKING SYSTEM")
    println()

    println("Please enter your login password.")
    println()
    val loggedInEmployee = EmployeeManagement.warehouseLogin()

    /**  need to change **/
    //val product11 = Product.products.find(_.productID == "0001")

    val product0 = new Product("0001", "Dagumi Fujiwara Gnome", "£8.60", "The Dagumi Fujiwara Gnome is the fastest downhill drift racer!", "AE86", false, 86)
    val product1 = new Product("0002", "Keisuke Takahashi Gnome", "£7.00", "The Keisuke Takahashi Gnome is the fastest up-hill drift racer!", "RX07", false, 70)
    val product2 = new Product("0003", "Penguin Gnome", "£3.00", "The Penguin Gnome is the coolest gnome around!", "AA01", false, 300)
    val product3 = new Product("0004", "Biggie Smalls Gnome", "£3.13", "The most gangsta gnome that ever lived. R.I.P.", "AA02", true, 313)

    val arrayOfAllProducts:Array[Product] = new Array[Product](4)

    arrayOfAllProducts(0) = product0
    arrayOfAllProducts(1) = product1
    arrayOfAllProducts(2) = product2
    arrayOfAllProducts(3) = product3

    /** Array of orders with products **/
    val productOrders:Array[ProductOrderList] = new Array[ProductOrderList](4)

    /** Customers products **/
    val dagumisProductOrder:Array[Product] = new Array[Product](25)

    for ( i <- 0 to (dagumisProductOrder.length - 1))
    {
      dagumisProductOrder(i) = product0
    }

    val keisukesProductOrder:Array[Product] = new Array[Product](25)

    for ( i <- 0 to (keisukesProductOrder.length - 1))
    {
      keisukesProductOrder(i) = product1
    }

    val rytisProductOrder:Array[Product] = new Array[Product](5)

    for ( i <- 0 to (4))
    {
      rytisProductOrder(i) = product2
    }

    val snoopLionsProductOrder:Array[Product] = new Array[Product](2)

    for ( i <- 0 to (1))
    {
      snoopLionsProductOrder(i) = product3
    }

    /**
      **/

    /** Adding customer's product order to product order list **/

    val customerProductOrder0 = new ProductOrderList("1086", "0001", "Dagumi Fujiwara", "86 Akina, Mount Haruna, Japan",dagumisProductOrder,false,"","")
    val customerProductOrder1 = new ProductOrderList("1007", "0007", "Keisuke Takahashi", "86 Akina, Mount Haruna, Japan",keisukesProductOrder,false,"","")
    val customerProductOrder2 = new ProductOrderList("1791", "0002", "Rytis the Grebe Murderer", "Prison",rytisProductOrder,false,"","")
    val customerProductOrder3 = new ProductOrderList("1420", "0003", "Snoop Lion", "Bakersfield, CA, USA" ,snoopLionsProductOrder,false,"","")

    productOrders(0) = customerProductOrder0
    productOrders(1) = customerProductOrder1
    productOrders(2) = customerProductOrder2
    productOrders(3) = customerProductOrder3

    /** purchase order ID, status, products, cost of purchase order **/

    /** Array of purchase orders **/

    val purchaseOrders:Array[PurchaseOrderList] = new Array[PurchaseOrderList](4)

    /** Purchase Order products **/

    val purchaseOrderProducts0:Array[Product] = new Array[Product](100)
    val purchaseOrderProducts1:Array[Product] = new Array[Product](200)
    val purchaseOrderProducts2:Array[Product] = new Array[Product](300)
    val purchaseOrderProducts3:Array[Product] = new Array[Product](400)

    populateArray(purchaseOrderProducts0)
    populateArray(purchaseOrderProducts1)
    populateArray(purchaseOrderProducts2)
    populateArray(purchaseOrderProducts3)

    def populateArray(array: Array[Product]): Unit ={
      for ( i <- 0 to array.length / 4)
      {
        array(i) = product0
      }
      for ( i <- ((array.length / 4) + 1) to (array.length / 2))
      {
        array(i) = product1
      }
      for ( i <- ((array.length / 2) + 1) to (array.length - (array.length / 4)))
      {
        array(i) = product2
      }
      for ( i <- ((array.length - (array.length / 4)) + 1) to array.length - 1)
      {
        array(i) = product3
      }
    }

    val purchaseOrder0 = new PurchaseOrderList("PO-0001", purchaseOrderProducts0, "£350", "In Transit")
    val purchaseOrder1 = new PurchaseOrderList("PO-0002", purchaseOrderProducts1, "£700", "In Transit")
    val purchaseOrder2 = new PurchaseOrderList("PO-0003", purchaseOrderProducts2,"£1050", "In Transit")
    val purchaseOrder3 = new PurchaseOrderList("PO-0004", purchaseOrderProducts3,"£1400", "In Transit")

    purchaseOrders(0) = purchaseOrder0
    purchaseOrders(1) = purchaseOrder1
    purchaseOrders(2) = purchaseOrder2
    purchaseOrders(3) = purchaseOrder3

    var continue = true

    while(continue) {
      println()
      println("What are you trying to manage?")

      println(" 1 - Orders")
      println(" 2 - Stock")
      println(" 3 - Exit")
      println()

      val choiceInput = scala.io.StdIn.readLine()
      choiceInput match {
        case "1" => println("Loading Orders...")
          println()
          NbGardensOrderList(productOrders, loggedInEmployee)

        case "2" => println("Loading Stock...")
          println()
          stockManagement(arrayOfAllProducts,productOrders,loggedInEmployee,purchaseOrders)

        case "3" => println("Goodbye " + loggedInEmployee + "!")
          continue = false

        case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
      }
    }
  }

  def stockManagement(productArray: Array[Product], pArray: Array[ProductOrderList],loggedEmp: String, purchaseOrderArray: Array[PurchaseOrderList])
  {
    var exit = false

    while(!exit) {

      println("NB Gardens Stock List!")
      println()

      if(showStockList) {
        displayStock(productArray)
      }

      println("What would you like to do with the Stock System?")
      println()
      println(" 1 - Decrement stock levels from checked out order(s) ")
      println(" 2 - Update Inventory System from purchase order(s) ")
      println(" 3 - Inform accounts of received purchase order(s) ")
      println(" 4 - Show / Hide Stock List")
      println(" 5 - Find a product ")
      println(" 6 - See what items need porousware from received purchase order(s)")
      println(" 7 - Go back")
      println()

      val stockChoice = scala.io.StdIn.readLine()

      stockChoice match {
        case "1" => println("Loading Inventory System...")

          decrementStockFromCheckedOrders(pArray,loggedEmp,productArray)

          //exit = true
        case "2" => println("Loading Inventory System...")

          updateInventoryFromPurchaseOrder(productArray,loggedEmp,purchaseOrderArray)

          println()
        case "3" => println("Loading Message System")
          println()
        case "4" =>
          if (showStockList) {
            showStockList = false
          }
          else {
            showStockList = true
          }
          println()
        case "5" => println("Loading Product Finder...")
          println()
          productFinder(productArray)
        case "6" => println("Loading porousware options...")
          println()
        case "7" => println("Loading options...")
          println()
          exit = true

        case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
      }

    }
  }

  def sendMessageToAccounts(): Unit =
  {
    println("Sending stock list to accounts")
  }

  def updateInventoryFromPurchaseOrder(allProductsArray: Array[Product],loggedEmp: String, purchaseOrderArray: Array[PurchaseOrderList]): Unit =
  {

    println()
    println("NB Gardens Purchase Orders")
    println()

    if(showPurchaseOrders)
    {
      displayPurchaseOrders(purchaseOrderArray,allProductsArray)
    }

    scala.io.StdIn.readLine()

  }

  def displayPurchaseOrders(purchaseOrderArray: Array[PurchaseOrderList],allProds: Array[Product]): Unit =
  {
    for ( i <- 0 to (purchaseOrderArray.length - 1))
    {
      purchaseOrderArray(i).purchaseOrderInformation()
      purchaseOrderArray(i).getPurchaseOrderProductsAndAmounts(allProds)
      println()
    }
  }

  def decrementStockFromCheckedOrders(pArray: Array[ProductOrderList],loggedEmp: String, allProductsArray: Array[Product]): Unit =
  {
    var exit = false

    while(!exit) {

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

          //pArray(i).getCustomerProducts()
          //pArray(i).getProductsAndAmounts(allProductsArray)

        }
      }
      if (checkedOrders == 0) {
        println(loggedEmp + ", you do not have any checked orders to decrement the Inventory System.")
        println()
        println("Any button to continue...")
        val anybutton = scala.io.StdIn.readLine()

        exit = true
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
            pArray(i).getProductsAndAmounts(allProductsArray)
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
              pArray(i).decrementStockLevels(allProductsArray) // decrement levels for that product order
              pArray(i).orderStatus = "Completed"
              found = true

              println()
              println("Any button to continue...")
              scala.io.StdIn.readLine()

            }
            else if(pArray(i).orderStatus == "Completed" && userInputOrderID == pArray(i).orderID)
            {
              if(loggedEmp == pArray(i).checkedOutBy)
              {
                println("You have already decremented this order.")
                found = true

                println()
                println("Any button to continue...")
                scala.io.StdIn.readLine()
              }
              else
              {
                println("This order has already been decremented from the Inventory System by another warehouse worker.")
                found = true
                println()
                println("Any button to continue...")
                scala.io.StdIn.readLine()
              }

            }
            else if(loggedEmp != pArray(i).checkedOutBy && userInputOrderID == pArray(i).orderID)
            {
              println("You have not checked out this order.")
              found = true
              println()
              println("Any button to continue...")
              scala.io.StdIn.readLine()
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
              println("Any button to continue...")
              scala.io.StdIn.readLine()
            }

          //exit = false
        }
        else if(userContinueChoice == "N" || userContinueChoice == "n" || userContinueChoice == "no" || userContinueChoice == "No")
        {
          println()
          exit = true
        }
        else
        {
          println("Sorry that input wasn't recognized.. ")
          println()
          //exit = false
        }
      }
    }
  }

  def productFinder(productArray: Array[Product]): Unit =
  {
    var productFound = false
    var continueSearch = true

    while(continueSearch)
    {

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

          continueSearch = findOtherProduct()
        }
        else if (productChoice == productArray(i).productID) {
          println("Product " + productArray(i).productID + " is located at section " + productArray(i).productLocation + " of the warehouse.")
          println("Warehouse maps can be located at the end of each aisle")

          productFound = true

          continueSearch = findOtherProduct()
        }
      }
      if (productFound == false)
      {
        println("Product not found...")

        continueSearch = findOtherProduct()
      }
    }
  }

  def findOtherProduct(): Boolean =
  {
    var continue = false

    println()
    println("Would you like to find another product? Y/N ")

    val productChoice2 = scala.io.StdIn.readLine()

    if(productChoice2 == "Y" || productChoice2 == "y" || productChoice2 == "yes" || productChoice2 == "Yes")
    {
      println()
      continue = true
    }
    else if(productChoice2 == "N" || productChoice2 == "n" || productChoice2 == "no" || productChoice2 == "No")
    {
      println()
      continue = false
    }
    else
    {
      println("Sorry that input wasn't recognized.. ")
      println()
      continue = true
    }

    continue
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

  /**{
    var employeeCorrectPass = false

    while(!employeeCorrectPass)
    {
      println("Please enter your login password.")
      println()

      val employeePassInput = scala.io.StdIn.readLine()

      if(employeePassInput == whArray(empID).employeePassword)
      {
        println()
        println("Logging you in " + whArray(empID).employeeName +  "!")
        println()
        employeeCorrectPass = true
      }

      if (employeeCorrectPass == false){
        println("Incorrect password..")
      }
    }
    whArray(empID).employeeName
  }**/

  var break = false

  def openAnOrder(pArray: Array[ProductOrderList], loggedEmp: String): Boolean =
  {
    var continue = false

    var continueOrderSearch = true

    while(continueOrderSearch) {

      break = false

      println("Select which order to open..")

      val input = scala.io.StdIn.readLine()

      var continueOrderOptions = true

      while (continueOrderOptions && (!break)) {

        //findOrder(input, productOrders)

        if (findOrder(input, pArray)) {

          continueOrderSearch = false

          println("What would you like to do with this customer order?")
          println(" 1 - Check out order")
          println(" 2 - Go back to all orders")
          println(" 3 - Go back to main menu")
          println(" 4 - Show / Hide Customer Products")
          println()
          val input2 = scala.io.StdIn.readLine()
          input2 match {
            case "1" => checkoutOrder(input, pArray, loggedEmp)
              continue = true
              continueOrderOptions = true
            case "2" => println("Loading orders...")
              println()
              continue = true
              continueOrderOptions = false
            case "3" => println("Loading menu...") //"Goodbye " +  loggedEmp +"!")
              continue = false
              continueOrderOptions = false
            case "4" =>
              if (showCustomerProducts) {
                showCustomerProducts = false
              }
              else {
                showCustomerProducts = true
              }
              continueOrderOptions = true
              continue = true

            case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
          }
        }
        else {
          continue = true
        }
      }
    }
    continue
  }

  var showCustomerOrders = true
  var showCustomerProducts = true
  var showStockList = true
  var showPurchaseOrders = true

  def NbGardensOrderList(pArray: Array[ProductOrderList],loggedEmp: String)
  {
    var continue = true

    while(continue) {
      println
      println("NB Gardens Order List!")
      println

      if(showCustomerOrders){
        displayOrders(pArray)
      }

      println("What would you like to do?")
      println("1 - Open an order")
      println("2 - Open my checked out orders")
      println("3 - Go back")
      println("4 - Show / Hide the Customer Order List")

      val userChoice = scala.io.StdIn.readLine()

      userChoice match {
        case "1" =>
          println()
          continue = openAnOrder(pArray,loggedEmp)

        case "2" => openCheckedOutOrders(pArray,loggedEmp)
          println()

        case "3" =>
          continue = false

        case "4" =>
          if(showCustomerOrders)
            {
              showCustomerOrders = false
            }
          else
            {
              showCustomerOrders = true
            }

          //continue = true

        case _ => continue = true
      }
    }
  }

  def displayOrders(pArray: Array[ProductOrderList])
  {
    for ( i <- 0 to (pArray.length - 1))
    {
      println(pArray(i).getOrderID() + " " + pArray(i).getCustomerName())
      println()
    }
  }

  def findOrder(userInput: String,pArray: Array[ProductOrderList]) : Boolean =
  {
    var found = false

    for ( i <- 0 to (pArray.length - 1)) {

      if (userInput == pArray(i).getOrderID()){

        println("Order " + userInput + " found!")
        println()
        found = true

        pArray(i).printOrderInformation()
        if (showCustomerProducts) {
          pArray(i).getCustomerProducts()
        }
        pArray(i).getStatus()

      }
    }

    if(!found)
    {
      println("Order ID not found..")
      println()
      break = true
    }

    found
  }

  def openCheckedOutOrders(pArray: Array[ProductOrderList],loggedInEmp: String)
  {
    println()
    println("Checked out orders for " + loggedInEmp)
    println()

    var x = 0

    for (i <- 0 to (pArray.length - 1))
    {
      if (loggedInEmp == pArray(i).checkedOutBy)
      {
        x = x + 1
        println(x + " - Order ID - " + pArray(i).getOrderID() + " Customer Name - " + pArray(i).getCustomerName())

      }
    }
    if(x == 0)
      {
        println("You have no checked out orders.")
      }
  }

  def checkoutOrder(userInput: String,pArray: Array[ProductOrderList], loggedInEmp: String)
  {
    for ( i <- 0 to (pArray.length - 1)) {

      if (userInput == pArray(i).getOrderID()){

        if((pArray(i).checkedOutStatus == false) && (pArray(i).orderStatus != "Pending")){

          pArray(i).checkedOutStatus = true
          pArray(i).checkedOutBy = loggedInEmp
          pArray(i).orderStatus = "Pending"

          println()
          println("Order - " + pArray(i).getOrderID() + " Checked out!")
          println()
        }
        else if(pArray(i).checkedOutBy == loggedInEmp)
        {
          println()
          println(loggedInEmp + ", you have already checked out this order..")
          println()
          println("Any button to continue...")
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