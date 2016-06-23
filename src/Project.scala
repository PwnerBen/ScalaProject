object Project {

  def main(args: Array[String]) {
    println("NB GARDENS WAREHOUSE ORDER TRACKING SYSTEM")
    println()

    println("Please enter your login password.")
    println()
    val loggedInEmployee = EmployeeManagement.warehouseLogin()

    mainMenu(loggedInEmployee)

  }

  def mainMenu(loggedInEmployee : String): Unit ={

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
        NbGardensOrderList(ProductOrderList.productOrders, loggedInEmployee)

      case "2" => println("Loading Stock...")
        println()
        stockManagement(AllProducts.arrayOfAllProducts,ProductOrderList.productOrders,loggedInEmployee,PurchaseOrders.purchaseOrders)

      case "3" => println("Goodbye " + loggedInEmployee + "!")
        //continue = false

      case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
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

        decrementStockFromCheckedOrders(pArray,loggedEmp,productArray,purchaseOrderArray)

      case "2" => println("Loading Inventory System...")

        updateInventoryFromPurchaseOrder(productArray,loggedEmp,purchaseOrderArray)

        println()

      case "3" => println("Loading Message System")
        println()
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)

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
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)

      case "7" => println("Loading options...")
        println()
        mainMenu(loggedEmp)

      case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
        stockManagement(productArray,pArray,loggedEmp,purchaseOrderArray)
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

      PurchaseOrderList.purchaseOrderInformation(purchaseOrderArray(i))
      PurchaseOrderList.getPurchaseOrderProductsAndAmounts(purchaseOrderArray(i),allProds)

      println()
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
      println("Any button to continue...")
      val anybutton = scala.io.StdIn.readLine()

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

              ProductOrderList.decrementStockLevels(pArray(i),allProductsArray) // decrement levels for that product order
              pArray(i).orderStatus = "Completed"
              found = true

              println()
              println("Any button to continue...")
              scala.io.StdIn.readLine()
              decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)

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
                decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)

              }
              else
              {
                println("This order has already been decremented from the Inventory System by another warehouse worker.")
                found = true
                println()
                println("Any button to continue...")
                scala.io.StdIn.readLine()
                decrementStockFromCheckedOrders(pArray,loggedEmp,allProductsArray,purchaseOrderArray)
              }

            }
            else if(loggedEmp != pArray(i).checkedOutBy && userInputOrderID == pArray(i).orderID)
            {
              println("You have not checked out this order.")
              found = true
              println()
              println("Any button to continue...")
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
            println("Any button to continue...")
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

    /**def filter(l1:List[Int], limit:Int) {
      def process(l2:List[Int]):List[Int] ={
        if (l2.isEmpty) l2
        else if (l2.head < limit)
          l2.head :: process(l2.tail)
        else
          process(l2.tail)
      }
      println(process(l1))
    }
    println(filter(
      List(1, 9, 2, 8, 3, 7, 4), 5))**/


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

  /** {
    *var employeeCorrectPass = false
    **
 *while(!employeeCorrectPass)
    *{
    *println("Please enter your login password.")
    *println()
 **
 *val employeePassInput = scala.io.StdIn.readLine()
 **
 *if(employeePassInput == whArray(empID).employeePassword)
      *{
    *println()
    *println("Logging you in " + whArray(empID).employeeName +  "!")
    *println()
    *employeeCorrectPass = true
    *}
 **
 *if (employeeCorrectPass == false){
    *println("Incorrect password..")
    *}
    *}
    *whArray(empID).employeeName
  *}**/

  var break = false

  def orderSelection(input : String, pArray : Array[ProductOrderList], loggedEmp : String): Unit =
  {
    if (findOrder(input, pArray)) {

      println("What would you like to do with this customer order?")
      println(" 1 - Check out order")
      println(" 2 - Go back to all orders")
      println(" 3 - Go back to main menu")
      println(" 4 - Show / Hide Customer Products")
      println()
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

        case _ => println("ERROR ERROR, SYSTEM MELTDOWN. SELF DESTRUCTING IN 5")
          orderSelection(input,pArray,loggedEmp)
      }
    }
    else {
      //openAnOrder(pArray,loggedEmp )
      NbGardensOrderList(pArray,loggedEmp)
    }
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
    println("1 - Open an order")
    println("2 - Open my checked out orders")
    println("3 - Go back")
    println("4 - Show / Hide the Customer Order List")

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

      case _ => NbGardensOrderList(pArray,loggedEmp)
    }
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

    for ( i <- 0 to (pArray.length - 1)) {

      if (userInput == pArray(i).orderID){

        println("Order " + userInput + " found!")
        println()
        found = true

        println("Order ID : " + pArray(i).orderID)
        println("Customer ID : " + pArray(i).customerID)
        println("Customer Name : " + pArray(i).customerName)
        println("Customer Address : " + pArray(i).customerAddress)

        if (showCustomerProducts) {
            ProductOrderList.getCustomerProducts(pArray(i))
        }

        if (pArray(i).checkedOutBy == "") {
          println("Order Checked out : " + pArray(i).checkedOutStatus)
          println
        }
        else {
          println("Order Checked out by " + pArray(i).checkedOutBy)
          println()
        }
      }
    }

    if(!found)
    {
      println("Order ID not found..")
      println()
      //break = true
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
        println(x + " - Order ID - " + pArray(i).orderID + " Customer Name - " + pArray(i).customerName)

      }
    }
    if(x == 0)
    {
      println("You have no checked out orders.")
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