/**
  * Created by Administrator on 17/06/2016.
  */
object EmployeeManagement {
  def warehouseLogin(): String ={
    println("Login - Enter your Name or Employee ID")
    println()
    val employee = WarehouseEmployee.findById(scala.io.StdIn.readLine()).get
    if(employee.employeeID.isEmpty)
      warehouseLogin()
    employee.employeeID
  }
}