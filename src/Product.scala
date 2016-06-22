/**
  * Created by Administrator on 17/06/2016.
  */
case class Product(val productID: String, val productName: String, val productCost: String, val productDescription: String, val productLocation: String,
                 var porouswareNeeded: Boolean, var stockLevel: Int)

object Product {

  var products = Seq(
    Product("0001", "Dagumi Fujiwara Gnome", "£8.60", "The Dagumi Fujiwara Gnome is the fastest downhill drift racer!", "AE86", false, 86),
    Product("0002", "Keisuke Takahashi Gnome", "£7.00", "The Keisuke Takahashi Gnome is the fastest up-hill drift racer!", "RX07", false, 70),
    Product("0003", "Penguin Gnome", "£3.00", "The Penguin Gnome is the coolest gnome around!", "AA01", false, 300),
    Product("0004", "Biggie Smalls Gnome", "£3.13", "The most gangsta gnome that ever lived. R.I.P.", "AA02", true, 313))

}