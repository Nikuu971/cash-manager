package com.epitech.cashmanager.article

/**
 * Data class for articles
 *
 * @property name type : String, name of the article
 * @property codeProduct type: String, article's product code
 * @property price type : Int, article's price
 */

data class Article(var name: String, var codeProduct: String, var price: Int)