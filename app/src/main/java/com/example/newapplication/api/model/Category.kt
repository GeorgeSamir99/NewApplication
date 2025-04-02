package com.example.newapplication.api.model

import com.example.newapplication.R

data class Category(
    val categoryTitle : Int? = null,
    val categoryImage : Int? = null,
    val endpointId : String? = null,
){
    companion object{
        private const val GENERAL = "general"
        private const val BUSINESS = "business"
        private const val SPORTS = "sports"
        private const val TECHNOLOGY = "technology"
        private const val SCIENCE ="science"
        private const val ENTERTAINMENT = "entertainment"
        private const val HEALTH = "health"

        fun categoriesList(): List<Category>{
            return listOf(
                getCategoryById(GENERAL),
                getCategoryById(BUSINESS),
                getCategoryById(SPORTS),
                getCategoryById(TECHNOLOGY),
                getCategoryById(SCIENCE),
                getCategoryById(ENTERTAINMENT),
                getCategoryById(HEALTH),
            )
        }

        private fun getCategoryById( id : String): Category {
            return when(id){
                
                GENERAL -> Category(
                    categoryTitle = R.string.general,
                    categoryImage = R.drawable.general_image,
                    endpointId = GENERAL

                )
                BUSINESS -> Category(
                    categoryTitle = R.string.business,
                    categoryImage = R.drawable.business_image,
                    endpointId = BUSINESS

                )
                SPORTS -> Category(
                    categoryTitle = R.string.sports,
                    categoryImage = R.drawable.sport_image,
                    endpointId = SPORTS

                )
                TECHNOLOGY -> Category(
                    categoryTitle = R.string.technology,
                    categoryImage = R.drawable.tech_image,
                    endpointId = TECHNOLOGY

                )
                SCIENCE -> Category(
                    categoryTitle = R.string.science,
                    categoryImage = R.drawable.science_image,
                    endpointId = SCIENCE

                )
                ENTERTAINMENT -> Category(
                    categoryTitle = R.string.entertainment,
                    categoryImage = R.drawable.entertainment_image,
                    endpointId = ENTERTAINMENT

                )
                HEALTH -> Category(
                    categoryTitle = R.string.health,
                    categoryImage = R.drawable.health_image,
                    endpointId = HEALTH

                )

                else -> Category(
                    categoryTitle = R.string.general,
                    categoryImage = R.drawable.general_image,
                    endpointId = GENERAL

                )
            }

        }
    }
}
