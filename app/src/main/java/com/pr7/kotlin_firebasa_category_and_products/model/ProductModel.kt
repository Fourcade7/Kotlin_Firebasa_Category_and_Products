package com.pr7.kotlin_firebasa_category_and_products

import android.net.Uri
import com.pr7.kotlin_firebasa_category_and_products.model.ImageModel

class ProductModel constructor(
    val  name:String?=null,
    val  imguri:String?=null,
    val  price:String?=null,
    val  description:String?=null,
    val  pushkey:String?=null,
):java.io.Serializable