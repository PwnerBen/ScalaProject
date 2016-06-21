/**
  * Created by Administrator on 17/06/2016.
  */
case class WarehouseEmployee(val employeeID: String, val employeeName: String, val employeePassword: String, val favouriteCar: String)

object WarehouseEmployee {
  var warehouseEmployees = Seq(WarehouseEmployee("0001", "Bunta Fujiwara",/**ihatetofu**/"a", "Subaru Impreza WRX STI Coupe Type R"),
    WarehouseEmployee("0002", "Shingo Shoji","NightKids", "Honda Civic SiR II EG6"))

  def findById(employeeID : String) = warehouseEmployees.find(_.employeeID == employeeID)

  def findByName(employeeName: String) = warehouseEmployees.find(_.employeeName == employeeName)

  def advancedFind(key: String) ={
    if(findById(key).nonEmpty) findById(key)
    else findByName(key)
  }
}
