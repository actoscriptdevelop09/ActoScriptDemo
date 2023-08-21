package com.example.actoscriptdemo.api
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FoodCategoryItemResponse (
  @SerializedName("SERVICERESPONSE" ) var SERVICERESPONSE : SERVICERESPONSE? = SERVICERESPONSE()
)

data class DETAILS (
  @SerializedName("ROWNUM"               ) var ROWNUM             : String? = null,
  @SerializedName("FOOD_CATEGORYID"      ) var FOODCATEGORYID     : String? = null,
  @SerializedName("FOOD_CATEGORYNAME"    ) var FOODCATEGORYNAME   : String? = null,
  @SerializedName("FOOD_CATEGORY_ITEMID" ) var FOODCATEGORYITEMID : String? = null,
  @SerializedName("ITEAMNAME"            ) var ITEAMNAME          : String? = null,
  @SerializedName("PRICE"                ) var PRICE              : String? = null,
  @SerializedName("QUANTITY"             ) var QUANTITY           : String? = null
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString()
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(ROWNUM)
    parcel.writeString(FOODCATEGORYID)
    parcel.writeString(FOODCATEGORYNAME)
    parcel.writeString(FOODCATEGORYITEMID)
    parcel.writeString(ITEAMNAME)
    parcel.writeString(PRICE)
    parcel.writeString(QUANTITY)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<DETAILS> {
    override fun createFromParcel(parcel: Parcel): DETAILS {
      return DETAILS(parcel)
    }

    override fun newArray(size: Int): Array<DETAILS?> {
      return arrayOfNulls(size)
    }
  }
}

data class DETAILSLIST (
  @SerializedName("DETAILS" ) var DETAILS : ArrayList<DETAILS> = arrayListOf()
)

data class SERVICERESPONSE (
  @SerializedName("RESPONSECODE"    ) var RESPONSECODE    : String?      = null,
  @SerializedName("RESPONSEMESSAGE" ) var RESPONSEMESSAGE : String?      = null,
  @SerializedName("CURRENTPAGE"     ) var CURRENTPAGE     : String?      = null,
  @SerializedName("TOTALPAGES"      ) var TOTALPAGES      : String?      = null,
  @SerializedName("TOTALRECORDS"    ) var TOTALRECORDS    : String?      = null,
  @SerializedName("DETAILSLIST"     ) var DETAILSLIST     : DETAILSLIST? = DETAILSLIST()
)