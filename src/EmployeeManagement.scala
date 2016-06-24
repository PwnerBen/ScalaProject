/**
  * Created by Administrator on 17/06/2016.
  */
object EmployeeManagement {
  def warehouseLogin(){
    println("Login - Enter your Name or Employee ID")
    println()

    val input = scala.io.StdIn.readLine()

    if(WarehouseEmployee.advancedFind(input).isEmpty)
    {
      println("Employee ID / Name not found..")
      warehouseLogin()
    }
    else {

      val tempEmployee = WarehouseEmployee.advancedFind(input).get
      println()
      println("Please enter your login password.")
      println()

      val password = scala.io.StdIn.readLine()
      if (tempEmployee.employeePassword != password) {
        println()
        println("Incorrect password..")
        println()
        warehouseLogin()
      }
      else
        {
          val employee = tempEmployee
          employee.employeeName
          Project.mainMenu(employee.employeeName)
        }

      //if (tempEmployee.employeeID.isEmpty || tempEmployee.employeeName.isEmpty)
       // warehouseLogin()
    }
  }
}