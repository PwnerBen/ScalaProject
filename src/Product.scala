/**
  * Created by Administrator on 17/06/2016.
  */
case class Product(val productID: String, val productName: String, val productCost: String, val productDescription: String, val productLocation: String,
                 var porouswareNeeded: Boolean, var stockLevel: Int)

object Product {

  var products = Array(
    Product("0001", "Dagumi Fujiwara Gnome", "£8.60", "The Dagumi Fujiwara Gnome is the fastest downhill drift racer!", "AE86", false, 30),
    Product("0002", "Keisuke Takahashi Gnome", "£7.00", "The Keisuke Takahashi Gnome is the fastest up-hill drift racer!", "RX07", true, 20),
    Product("0003", "Penguin Gnome", "£3.00", "The Penguin Gnome is the coolest gnome around!", "AA01", false, 56),
    Product("0004", "Biggie Smalls Gnome", "£3.13", "The most gangsta gnome that ever lived. R.I.P.", "AA02", true, 42))

  /**
  var product0 = new Product("0001", "Dagumi Fujiwara Gnome", "£8.60", "The Dagumi Fujiwara Gnome is the fastest downhill drift racer!", "AE86", false, 86)
  var product1 = new Product("0002", "Keisuke Takahashi Gnome", "£7.00", "The Keisuke Takahashi Gnome is the fastest up-hill drift racer!", "RX07", false, 70)
  var product2 = new Product("0003", "Penguin Gnome", "£3.00", "The Penguin Gnome is the coolest gnome around!", "AA01", false, 300)
  var product3 = new Product("0004", "Biggie Smalls Gnome", "£3.13", "The most gangsta gnome that ever lived. R.I.P.", "AA02", true, 313)
**/

  def findById(productID : String) = products.find(_.productID == productID)

  def findByName(productName: String) = products.find(_.productName == productName)




}
