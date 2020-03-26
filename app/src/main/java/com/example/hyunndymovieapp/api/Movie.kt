package com.example.hyunndymovieapp.api

import android.os.Parcel
import android.os.Parcelable
import java.net.URL

class Movie : Parcelable {
    var id:Int? = 0
    var title:String? = null
    var title_eng:String? = null
    var date:String? = null
    var user_rating:Float? = 0.0f
    var audience_rating:Float? = 0.0f
    var reviewer_rating:Float? = 0.0f
    var reservation_rate:Float? = 0.0f
    var reservation_grade:Int? = 0
    var grade:Int? = 0
    var thumb: String? = null
    var image: String? = null

    companion object {
        @JvmField

        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel?): Movie {

                var movie = Movie()

                movie.image = source?.readString()
                movie.title = source?.readString()
                movie.date = source?.readString()
                movie.audience_rating = source?.readFloat()
                movie.reservation_rate = source?.readFloat()
                movie.grade = source?.readInt()

                return movie
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls<Movie>(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

        dest?.writeString(image)
        dest?.writeString(title)
        dest?.writeString(date)
        dest?.writeFloat(audience_rating!!)
        dest?.writeFloat(reservation_rate!!)
        dest?.writeInt(grade!!)
    }

    override fun describeContents(): Int {
        return 0
    }
}