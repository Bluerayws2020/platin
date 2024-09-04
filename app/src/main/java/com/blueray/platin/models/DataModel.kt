package com.blueray.platin.models

data class GetCountriesResponse(
    val `data`: List<GetCountriesData>,
    val status: Int
)

data class GetCountriesData(
    val id: Int,
    val name: String,
    val phone_code: String,
    val status: String
)

data class GetCitiesResponse(
    val `data`: List<GetCitiesData>,
    val status: Int
)

data class GetCitiesData(
    val id: Int,
    val name: String
)

data class IndvisualRegisterResponse(
    val `data`: IndvisualRegisterData,
    val message: String,
    val status: Int
)

data class IndvisualRegisterData(
    val customer: Customer,
    val token: String
)

data class Customer(
    val building_name_number: String,
    val city_id: String,
    val country_id: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String
)


data class TraderRegisterResponse(
    val `data`: TraderRegisterData,
    val message: String,
    val status: Int
)

data class TraderRegisterData(
    val customer: Trader,
    val token: String
)

data class Trader(
    val building_name_number: String,
    val city_id: String,
    val commercial_register: String,
    val commercial_register_url: String,
    val commerical_name: String,
    val country_id: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val profession_licence: String,
    val profession_licence_url: String,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String
)

data class LoginResponse(
    val `data`: LoginData,
    val message: String,
    val status: Int
)

data class LoginData(
    val customer: LoginCustomer,
    val token: String
)

data class LoginCustomer(
    val building_name_number: String,
    val city_id: Int,
    val commercial_register: Any,
    val commerical_name: Any,
    val country_id: Int,
    val created_at: String,
    val deleted_at: Any,
    val email: String,
    val email_verified_at: Any,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val profession_licence: Any,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String
)

data class GetMyProfileResponse(
    val `data`: GetMyProfileData,
    val status: Int
)

data class GetMyProfileData(
    val building_name_number: String,
    val city_id: Int,
    val commercial_register: Any,
    val commerical_name: Any,
    val country_id: Int,
    val created_at: String,
    val deleted_at: Any,
    val email: String,
    val email_verified_at: Any,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val profession_licence: Any,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String
)

data class LogoutResponse(
    val `data`: String,
    val message: String,
    val status: Int
)

data class GetCategoriesResponse(
    val `data`: List<GetCategoriesData>,
    val status: Int
)

data class GetCategoriesData(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val welcome_image: WelcomeImage
)

class WelcomeImage

data class GetSubCategoriesResponse(
    val `data`: List<SubCategoriesData>,
    val status: Int
)

data class SubCategoriesData(
    val id: Int,
    val image: String,
    val name: String,
    val parent_id: Int,
    val status: String
)


data class GetProductsForCategoryResponse(
    val `data`: GetProductsForCategoryData,
    val pagination: Pagination,
    val status: Int
)

data class GetProductsForCategoryData(
    val products: List<Product>,
    val sub_categories: List<SubCategory>
)

data class Product(
    val category_id: Int,
    val id: Int,
    val image: String,
    val is_favorited: Boolean,
    val name: String,
    val price: String,
    val product_other_images: List<String>,
    val product_review: Int,
    val status: String
)

data class Pagination(
    val current_page: Int,
    val last_page: Int,
    val next_page_url: String,
    val per_page: Int,
    val prev_page_url: Any,
    val total: Int
)

data class SubCategory(
    val id: Int,
    val name: String
)


data class GetProductDetailsResponse(
    val `data`: ProductDetailsData,
    val status: Int
)

data class ProductDetailsData(
    val description: String,
    val id: Int,
    val image: String,
    val is_favorited: Boolean,
    val name: String,
    val other_images: List<String>,
    val productVariations: ProductVariations,
    val product_review: Int,
    val status: String,
    val variation_type: String
)

data class ProductVariations(
    val color: List<Color>,
    val price: String,
    val size: List<Size>
)

data class Size(
    val name: String,
    val size_id: Int
)

data class Color(
    val color_id: Int,
    val name: String
)

data class GetColorsBySizeResponse(
    val status: Int,
    val `data`: List<Color>
)

data class GetVariationPriceResponse(
    val `data`: GetVariationPriceData,
    val status: Int
)

data class GetVariationPriceData(
    val id: Int,
    val price: String
)

data class GetMyCartResponse(
    val `data`: GetMyCartData,
    val status: Int
)

data class GetMyCartData(
    val customer_cart: List<CustomerCart>,
    val endTotal: Double,
    val numberOfProducts: Int,
    val subTotal: Double
)

data class CustomerCart(
    val color: String,
    val color_id: Int,
    val customer_id: Int,
    val id: Int,
    val itemEndTotal: Double,
    val itemSubTotal: Double,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val quantity: Int,
    val size: String,
    val size_id: Int,
    val variation: Variation,
    val variation_id: Int
)

data class Variation(
    val box_cost: String,
    val box_on_sale_price: String,
    val box_on_sale_price_status: String,
    val box_quantity: Int,
    val box_sale_price: String,
    val color_id: Int,
    val cost_price: String,
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val on_sale_price: String,
    val on_sale_price_status: String,
    val product_id: Int,
    val quantity: Int,
    val sale_price: String,
    val size_id: Int,
    val status: String,
    val updated_at: String
)

data class AboutUsResponse(
    val `data`: AboutUsData,
    val status: Int
)

data class AboutUsData(
    val aboutDescription: String,
    val aboutTitle: String,
    val distinguishUsDescription: String,
    val distinguishUsTitle: String,
    val id: Int,
    val ourHistoryDescription: String,
    val ourHistoryTitle: String
)

data class GetCertificationsResponse(
    val `data`: List<GetCertificationsData>,
    val status: Int
)

data class GetCertificationsData(
    val id: Int,
    val image: String,
    val name: String,
    val pdf_file: String,
    val status: String
)

data class GetOurCompaniesResponse(
    val `data`: List<GetOurCompaniesData>,
    val status: Int
)

data class GetOurCompaniesData(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val url: String
)

data class GetOffersResponse(
    val `data`: List<Product>,
    val pagination: Pagination,
    val status: Int
)

data class GetRandomOffersResponse(
    val `data`: List<Product>,
    val status: Int
)


data class GetBrandsResponse(
    val `data`: List<BrandsData>,
    val status: Int
)

data class BrandsData(
    val id: Int,
    val image: String,
    val name: String,
    val status: String
)