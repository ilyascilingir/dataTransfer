package com.bakanlik112.mobil.datatransfer.model


class MySingleton {

    companion object {

        private var instance: MySingleton? = null

        var singletonUserInfo : SingletonUserInfo? = null

        fun getInstance(): MySingleton {
            if (instance == null) {
                instance = MySingleton()
            }
            return instance as MySingleton
        }
    }

}
