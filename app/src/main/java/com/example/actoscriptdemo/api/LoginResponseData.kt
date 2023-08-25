package com.example.actoscriptdemo.api

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("SERVICERESPONSE" ) var LOGINSERVICERESPONSE : LOGINSERVICERESPONSE? = LOGINSERVICERESPONSE()
)

data class LOGINUSERDETAILS (

    @SerializedName("LOGINID"       ) var LOGINID       : Int?    = null,
    @SerializedName("PASSWORD"      ) var PASSWORD      : String? = null,
    @SerializedName("TOKEN"         ) var TOKEN         : String? = null,
    @SerializedName("EMPID"         ) var EMPID         : Int?    = null,
    @SerializedName("USERNAME"      ) var USERNAME      : String? = null,
    @SerializedName("EMPCODE"       ) var EMPCODE       : String? = null,
    @SerializedName("MOBILENO"      ) var MOBILENO      : Int?    = null,
    @SerializedName("DOJ"           ) var DOJ           : String? = null,
    @SerializedName("DOB"           ) var DOB           : String? = null,
    @SerializedName("SHORTNAME"     ) var SHORTNAME     : String? = null,
    @SerializedName("FULLNAME"      ) var FULLNAME      : String? = null,
    @SerializedName("GENDER"        ) var GENDER        : String? = null,
    @SerializedName("EMAILID"       ) var EMAILID       : String? = null,
    @SerializedName("ISVOUCEHREDIT" ) var ISVOUCEHREDIT : Int?    = null,
    @SerializedName("USERGROUPNAME" ) var USERGROUPNAME : String? = null,
    @SerializedName("TCSPER"        ) var TCSPER        : Int?    = null,
    @SerializedName("TCSAMTLIMIT"   ) var TCSAMTLIMIT   : Int?    = null,
    @SerializedName("ISCHECKIN"     ) var ISCHECKIN     : Int?    = null,
    @SerializedName("PHOTOPATH"     ) var PHOTOPATH     : String? = null,
    @SerializedName("CITYID"        ) var CITYID        : Int?    = null

)

data class LOGINSERVICERESPONSE (
    @SerializedName("USERDETAILS"  ) var USERDETAILS  : LOGINUSERDETAILS? = LOGINUSERDETAILS(),
    @SerializedName("RESPONSECODE" ) var RESPONSECODE : Int?         = null
)