/**
  * Created by Ben on 22/06/2016.
  */
object PurchaseOrders {

  /** Array of purchase orders **/



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
    for ( i <- 0 to (array.length / 4) - 1)
    {
      array(i) = Product.findById("0001").get
    }
    for ( i <- ((array.length / 4)) to (array.length / 2) - 1)
    {
      array(i) = Product.findById("0002").get
    }
    for ( i <- ((array.length / 2)) to (array.length - (array.length / 4) - 1))
    {
      array(i) = Product.findById("0003").get
    }
    for ( i <- ((array.length - (array.length / 4)) to array.length - 1))
    {
      array(i) = Product.findById("0004").get
    }
  }

  val purchaseOrders = Array(PurchaseOrderList("PO-0001", purchaseOrderProducts0, "£350", "In Transit"),
                             PurchaseOrderList("PO-0002", purchaseOrderProducts1, "£700", "In Transit"),
                             PurchaseOrderList("PO-0003", purchaseOrderProducts2, "£1050", "In Transit"),
                             PurchaseOrderList("PO-0004", purchaseOrderProducts3,"£1400", "In Transit"))

  def findByPOID(purchaseOrderID: String) = purchaseOrders.find(_.purchaseOrderID == purchaseOrderID)


  //val purchaseOrders:Array[PurchaseOrderList] = new Array[PurchaseOrderList](4)

}
