package com.narsha.network


object BuildLabUrl {
    const val BASE_URL = "https://api.buildlab.kr"

    const val AUTH = "$BASE_URL/auth"

    object Auth {
        const val VERIFY = "$AUTH/verify"
        const val SIGNUP = "$AUTH/signup"
        const val SEND = "$AUTH/send"
        const val REFRESH = "$AUTH/reissue"
        const val LOGIN = "$AUTH/login"
        const val GOOGLE = "$LOGIN/google"
    }

}