package tellabs.android.basekotlin.data.db.entity
/**
  BCT
 */
// class Sale(){
//     var total : Double = 0.0
//     var saleItem = mutableListOf<SaleItemDetail>()
// }
// class SaleItemDetail(){
//     var namaItem : String = ""
//     var qty : Int = 0
//     var balance : Int = 0
//     var price : Double=0.0
//}
data class Sale(
    var total : Double = 0.0,
    var saleItem : MutableList<SaleItemDetail> = mutableListOf(SaleItemDetail())
)
data class SaleItemDetail(
    var namaItem : String = "",
    var qty : Int = 0,
    var balance : Int = 0,
    var price : Double=0.0
)
